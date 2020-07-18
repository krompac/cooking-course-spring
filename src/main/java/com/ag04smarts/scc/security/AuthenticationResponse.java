package com.ag04smarts.scc.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Date;

public class AuthenticationResponse {
    private String token;
    private String username;
    private String role;
    private Date expirationDate;

    public AuthenticationResponse(String jwt, String username, Date expirationDate, ArrayList<GrantedAuthority> authorities) {
        this.token = jwt;
        this.username = username;
        this.expirationDate = expirationDate;
        this.role = authorities.get(0).getAuthority();
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
