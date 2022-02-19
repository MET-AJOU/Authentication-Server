package com.metajou.authentication.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.beans.ConstructorProperties;

@Getter
@ToString
@ConfigurationProperties(prefix = "spring.metajou.secret")
@RequiredArgsConstructor
@ConstructorBinding
public class SecretProperties {
    @Qualifier("serverkey")
    private final String SERVER_KEY;
    @Qualifier("createtokenkey")
    private final String CREATE_TOKEN_KEY;
}
