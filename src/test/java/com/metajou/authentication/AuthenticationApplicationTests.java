package com.metajou.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        properties =
        "spring.config.location=" +
        "classpath:/application.properties" +
        ",classpath:/secret.properties"
)
class AuthenticationApplicationTests {

    @Value("${secret-key}")
    private String secret_key;

    @Test
    void contextLoads() {
        System.out.println(secret_key);
    }

}
