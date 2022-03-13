package com.metajou.authserver.config;

import com.metajou.authserver.security.CustomOauth2LoginSuccessHandler;
import com.metajou.authserver.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

@EnableWebFluxSecurity
@ComponentScan(basePackages = {
        "com.metajou.authserver.security"
})
public class SecurityConfig {

    private final CustomOauth2LoginSuccessHandler customOauth2LoginSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomOauth2LoginSuccessHandler customOauth2LoginSuccessHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customOauth2LoginSuccessHandler = customOauth2LoginSuccessHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.requestCache()
                .requestCache(NoOpServerRequestCache.getInstance())
                .and().securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        http.oauth2Login(Customizer.withDefaults())
                .oauth2Login(oAuth2LoginSpec -> {
                    oAuth2LoginSpec.authenticationSuccessHandler(customOauth2LoginSuccessHandler);
                });

        http.addFilterAfter(jwtAuthenticationFilter, SecurityWebFiltersOrder.LOGOUT);

        return http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/api/**").authenticated()
                .anyExchange().permitAll()
                .and().build();
    }

}
