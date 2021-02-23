package com.finance.bravenewcoinBack.security.dto;

import com.finance.bravenewcoinBack.security.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String username;
    private User user;
    private Collection<? extends GrantedAuthority> roles;

    public JwtDto(User user, String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.token = token;
        this.username = username;
        this.roles = authorities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.roles = authorities;
    }
}
