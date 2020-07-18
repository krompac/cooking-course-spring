package com.ag04smarts.scc.security;

import com.ag04smarts.scc.model.OauthUser;
import com.ag04smarts.scc.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();
    private Boolean enabled;

    public MyUserDetails() {
    }

    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities.add(new SimpleGrantedAuthority(user.getRoles()));
        this.enabled = user.getActive();
    }

    public MyUserDetails(OauthUser user) {
        this.username = user.getName();
        this.password = null;
        this.authorities.add(new SimpleGrantedAuthority(user.getRole()));
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
