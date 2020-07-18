package com.ag04smarts.scc.controller;


import com.ag04smarts.scc.model.AccessToken;
import com.ag04smarts.scc.model.OauthUser;
import com.ag04smarts.scc.repository.OauthUserRepository;
import com.ag04smarts.scc.security.AuthenticationRequest;
import com.ag04smarts.scc.security.AuthenticationResponse;
import com.ag04smarts.scc.security.MyUserDetails;
import com.ag04smarts.scc.security.MyUserDetailsService;
import com.ag04smarts.scc.security.util.JwtUtil;
import com.ag04smarts.scc.services.OauthService;
import com.ag04smarts.scc.services.SocialProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class AuthController {
    private final SocialProviderService socialProviderService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final OauthUserRepository oauthUserRepository;
    private final OauthService oauthService;
    private final JwtUtil jwtUtil;

    public AuthController(SocialProviderService socialProviderService, AuthenticationManager authenticationManager,
                          MyUserDetailsService myUserDetailsService, OauthUserRepository oauthUserRepository,
                          OauthService oauthService, JwtUtil jwtUtil) {
        this.socialProviderService = socialProviderService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.oauthUserRepository = oauthUserRepository;
        this.oauthService = oauthService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/logoutUser")
    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/validateLogin")
    public AuthenticationResponse validateLogin(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        Date expirationDate = jwtUtil.extractExpirationDate(jwt);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

        return new AuthenticationResponse(jwt, authenticationRequest.getUsername(), expirationDate, authorities);
    }

    @PostMapping("/oauth/login/{provider}")
    public AuthenticationResponse oauthLogin(@RequestBody String code, @PathVariable String provider) {
        if (code.startsWith("code")) {
            code = code.substring(5);

            if (code.endsWith("&submit=value")) {
                code = code.substring(0, code.indexOf("&submit=value"));
            }

            code = code.replace('%', '/');
        }

        Logger logger = LoggerFactory.getLogger(AuthController.class);

        AccessToken token = oauthService.getAccessToken(code, provider);
        OauthUser user = oauthService.getUser(token, provider);

        if (user != null) {
            String jwt = jwtUtil.generateOauthToken(user.getName(), token, user.getOauthId(), provider);
            Date expirationDate = jwtUtil.extractExpirationDate(jwt);
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            oauthUserRepository.findByOauthIdAndName(user.getOauthId(), user.getName())
                    .ifPresentOrElse(oauthUser -> logger.info("user already in database..."),
                            () -> oauthUserRepository.save(user));

            return new AuthenticationResponse(jwt, user.getName(), expirationDate, authorities);
        }

        throw new BadCredentialsException("Something went wrong...");
    }

    @CrossOrigin
    @GetMapping("/oauth/authorizationUri")
    public ResponseEntity<?> getAuthorizationUri() {
        Map<String, String> authorizationUris = new HashMap<>();
        authorizationUris.put("facebook", socialProviderService.getAuthorizationUri("facebook"));
        authorizationUris.put("github", socialProviderService.getAuthorizationUri("github"));
        authorizationUris.put("google", socialProviderService.getAuthorizationUri("google"));

        return new ResponseEntity<>(authorizationUris, HttpStatus.OK);
    }
}
