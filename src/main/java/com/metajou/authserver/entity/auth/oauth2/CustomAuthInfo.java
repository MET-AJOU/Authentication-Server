package com.metajou.authserver.entity.auth.oauth2;

import com.metajou.authserver.entity.auth.AuthInfo;

public interface CustomAuthInfo {
    public AuthInfo extractAuthInfo();
}
