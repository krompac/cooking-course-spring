package com.ag04smarts.scc.security.filter;

import com.ag04smarts.scc.model.AccessToken;
import com.ag04smarts.scc.model.OauthUser;
import com.ag04smarts.scc.security.util.JwtUtil;
import com.ag04smarts.scc.security.MyUserDetails;
import com.ag04smarts.scc.security.MyUserDetailsService;
import com.ag04smarts.scc.services.OauthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final OauthService oauthService;
    private final MyUserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, MyUserDetailsService userDetailsService, OauthService oauthService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.oauthService = oauthService;
    }

    private void setContextAuthentication(HttpServletRequest request, MyUserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;
        AccessToken accessToken = null;
        String oauthId = null;
        String provider = null;
        String userType;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            userType = jwtUtil.extractClaim(jwt,"userType");

            if (userType != null && userType.equals("oauth")) {
                accessToken = jwtUtil.extractAccessToken(jwt);
                oauthId = jwtUtil.extractClaim(jwt, "oauthId");
                provider = jwtUtil.extractClaim(jwt, "provider");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (accessToken != null && oauthId != null && provider != null) {
                OauthUser oauthUser = oauthService.loadAndVerifyOauthUser(jwt, accessToken, username, oauthId, provider);
                setContextAuthentication(request, new MyUserDetails(oauthUser));
            } else {
                MyUserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    setContextAuthentication(request, userDetails);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
