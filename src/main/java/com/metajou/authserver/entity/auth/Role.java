package com.metajou.authserver.entity.auth;

import com.metajou.authserver.entity.oauth2.OAuth2Provider;
import org.springframework.data.r2dbc.convert.EnumWriteSupport;

public enum Role {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_ADMIN
}
class RoleConverter extends EnumWriteSupport<Role> {
}