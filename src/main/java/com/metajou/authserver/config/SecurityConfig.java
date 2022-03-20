package com.metajou.authserver.config;

import com.metajou.authserver.security.CustomOauth2LoginSuccessHandler;
import com.metajou.authserver.security.JwtAuthenticationFilter;
import com.metajou.authserver.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@EnableWebFluxSecurity
@ComponentScan(basePackages = {
        "com.metajou.authserver.security",
        "com.metajou.authserver.util"
})
public class SecurityConfig {

    private final CustomOauth2LoginSuccessHandler customOauth2LoginSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomOauth2LoginSuccessHandler customOauth2LoginSuccessHandler, JwtUtils jwtUtils) {
        this.customOauth2LoginSuccessHandler = customOauth2LoginSuccessHandler;
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        //TODO Make Stateless
        //http.requestCache().requestCache(NoOpServerRequestCache.getInstance());
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        //TODO OAuth2Login
        http.oauth2Login(Customizer.withDefaults())
                .oauth2Login(oAuth2LoginSpec -> {
                    oAuth2LoginSpec.authenticationSuccessHandler(customOauth2LoginSuccessHandler);
                });

        //TODO ADD CUSTOM Filter
        http.addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.OAUTH2_AUTHORIZATION_CODE);

        //TODO ETC
        http.csrf().disable();
        http.cors().and();
        http.formLogin().disable();
        http.logout().disable();

        return http.authorizeExchange()
                .pathMatchers("/api").permitAll()
                .pathMatchers("/admin/makemeadmin").permitAll()
                .pathMatchers("/api/**").authenticated()
                .pathMatchers("/admin/**").hasRole("ADMIN")
                .anyExchange().permitAll()
                .and().build();
    }

}
