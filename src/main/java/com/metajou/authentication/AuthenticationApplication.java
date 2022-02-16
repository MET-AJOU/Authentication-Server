package com.metajou.authentication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuthenticationApplication {
    private static final String PROPERTIES = "spring.config.location=" +
            "classpath:/application.properties" +
            ",classpath:/secret.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthenticationApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }

}
