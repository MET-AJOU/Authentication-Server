package com.metajou.authserver.entity.auth;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication {

    @Getter
    private final String userCode;
    @Getter
    private final String userEmail;
    @Getter
    private final Claims claims;
    @Getter
    private final String accessToken;

    @Getter
    private final ArrayList<? extends GrantedAuthority> authorities;
    @Setter
    private Boolean isAuthenticated = true;

    public CustomAuthentication(String userCode, Claims claims, String accessToken) {
        this.userCode = userCode;
        this.claims = claims;
        this.userEmail = claims.get("email", String.class);
        List<String> rolesMap = claims.get("role", List.class);
        this.authorities = (ArrayList<? extends GrantedAuthority>) rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.accessToken = accessToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return this.getAccessToken();
    }

    @Override
    public Object getDetails() {
        return this.getClaims();
    }

    @Override
    public Object getPrincipal() {
        CustomUser user = new CustomUser(getUserCode(), getUserEmail(), getAccessToken());

        //TODO 유저 상태 Set 해주기
        // ex: user.setIsAuthenticated(true);

        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.userCode;
    }
}
