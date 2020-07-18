package com.ag04smarts.scc.security.util;

import com.ag04smarts.scc.model.AccessToken;
import com.ag04smarts.scc.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${secretkey}")
    private String SECRET_KEY;
    private Logger logger;

    public JwtUtil() {
        logger = LoggerFactory.getLogger(JwtUtil.class);
    }

    public Boolean validateToken(String token, MyUserDetails userDetails) {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(MyUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        final String username = userDetails.getUsername();

        return createToken(claims, username);
    }

    public String generateOauthToken(String username, AccessToken accessToken, String oauthId, String provider) {
        Map<String, Object> claims = new HashMap<>();

        return createOauthToken(claims, username, accessToken, oauthId, provider);
    }

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            logger.error("USERNAME");
            return null;
        }
    }

    public String extractClaim(String token, String claimName) {
        try {
            return extractAllClaims(token).get(claimName, String.class);
        } catch (Exception e) {
            logger.error("CLAIM");
            return null;
        }
    }

    public AccessToken extractAccessToken(String token) {
        try {
            LinkedHashMap accessTokenData = extractAllClaims(token).get("accessToken", LinkedHashMap.class);
            return new AccessToken(accessTokenData.get("access_token").toString(), accessTokenData.get("token_type").toString());
        } catch (Exception e) {
            logger.error("ACCESS TOKEN");
            return null;
        }
    }

    private String createOauthToken(Map<String, Object> claims, String subject, AccessToken accessToken,
                                    String oauthId, String provider) {
        return getBuilder(claims, subject)
                .claim("accessToken", accessToken)
                .claim("oauthId", oauthId)
                .claim("provider", provider)
                .claim("userType", "oauth")
                .compact();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return getBuilder(claims, subject).claim("userType", "normal").compact();
    }

    private JwtBuilder getBuilder(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            logger.error("CLAIM TEMPLATE");
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
