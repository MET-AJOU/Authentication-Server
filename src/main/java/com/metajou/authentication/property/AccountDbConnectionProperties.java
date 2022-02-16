package com.metajou.authentication.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ToString
@ConfigurationProperties(prefix = "spring.datasource.account")
@RequiredArgsConstructor
@ConstructorBinding
public class AccountDbConnectionProperties {
    private final String database;
    private final String driver;
    private final String host;
    private final Integer port;
    private final String user;
    private final String password;
}

