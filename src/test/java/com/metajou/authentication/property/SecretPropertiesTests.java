package com.metajou.authentication.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(value = {"classpath:secret.properties"})
@EnableConfigurationProperties(value = {SecretProperties.class})
public class SecretPropertiesTests {

    @Autowired
    private SecretProperties properties;
    @Value("${spring.metajou.secret.serverkey}")
    private String serverKey;
    @Value("${spring.metajou.secret.createtokenkey}")
    private String createTokenKey;

    @Test
    public void checkSecretPropertiesAvailable() {
        Assertions.assertNotNull(properties);
        Assertions.assertEquals(properties.getSERVER_KEY(), serverKey);
        Assertions.assertEquals(properties.getCREATE_TOKEN_KEY(), createTokenKey);
    }

}
