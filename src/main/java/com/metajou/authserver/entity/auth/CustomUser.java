package com.metajou.authserver.entity.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class CustomUser implements UserDetails {

    @Getter
    private final String userCode;
    @Getter
    private final String userEmail;
    @Getter
    private final String accessToken;

    @Setter @Getter
    private List<Role> roles;
    @Setter
    private Boolean isAuthenticated = true;
    @Setter
    private Boolean isAccountNonExpired = true;
    @Setter
    private Boolean isAccountNonLocked = true;
    @Setter
    private Boolean isCredentialsNonExpired = true;
    @Setter
    private Boolean isEnabled = true;

    public CustomUser(String userCode, String userEmail, String accessToken) {
        this.userCode = userCode;
        this.userEmail = userEmail;
        this.accessToken = accessToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
