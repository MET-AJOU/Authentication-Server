package com.metajou.authentication.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ToString
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
@RequiredArgsConstructor
@ConstructorBinding
public class OAuth2Properties {
    @Qualifier("client-id")
    private final String googleClientId;
    @Qualifier("client secret")
    private final String googleClientSecret;
    @Qualifier("google.scope")
    private final String scope;
}
