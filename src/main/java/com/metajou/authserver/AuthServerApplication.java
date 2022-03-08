package com.metajou.authserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuthServerApplication {

    private static final String PROPERTIES = "spring.config.location=" +
            "classpath:/application.properties" +
            ",classpath:/secret.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthServerApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }
}
