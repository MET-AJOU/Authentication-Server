package com.metajou.authserver.entity.auth.oauth2;

import org.springframework.data.r2dbc.convert.EnumWriteSupport;

public enum OAuth2Provider {
    GOOGLE,
    KAKAO
}

class OAuth2ProviderConverter extends EnumWriteSupport<OAuth2Provider> {
}