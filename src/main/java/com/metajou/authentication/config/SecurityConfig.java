package com.metajou.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    ReactiveClientRegistrationRepository clientRegistrations() {
//        ClientRegistration clientRegistration = ClientRegistrations
//                .fromOidcIssuerLocation("https://idp.example.com/auth/~~/~~")
//                .clientId("id")
//                .clientSecret("key")
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
//    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/api/**").authenticated()
                .anyExchange().permitAll()
                .and().oauth2Login(Customizer.withDefaults())
                .logout().logoutUrl("/account/logout")
                .and().build();
    }

}
