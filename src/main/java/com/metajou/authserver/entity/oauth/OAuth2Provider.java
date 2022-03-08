package com.metajou.authserver.entity.oauth;

import org.springframework.data.r2dbc.convert.EnumWriteSupport;

public enum OAuth2Provider {
    GOOGLE,
    KAKAO
}

class OAuth2ProviderConverter extends EnumWriteSupport<OAuth2Provider> {
}