package com.metajou.authserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebFlux
public class CorsGlobalConfig implements WebFluxConfigurer {

    @Value("${spring.client.cors.url}")
    private String[] clientUrl;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(clientUrl)
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600L);
    }

}
