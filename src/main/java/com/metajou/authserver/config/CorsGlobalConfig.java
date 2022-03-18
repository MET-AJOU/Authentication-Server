package com.metajou.authserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfig implements WebFluxConfigurer {

    @Value("${spring.client.cors.url}")
    private String[] cilentUrl;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(cilentUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3600);
    }

}
