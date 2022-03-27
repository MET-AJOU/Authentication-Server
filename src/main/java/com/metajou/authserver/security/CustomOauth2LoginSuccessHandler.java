package com.metajou.authserver.security;

import com.metajou.authserver.entity.auth.oauth2.OAuth2UserInfo;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CustomOauth2LoginSuccessHandler extends RedirectServerAuthenticationSuccessHandler {

    @Value("${spring.client.webserver.url}")
    private String webServerUrl;
    @Value("${spring.security.oauth2.client.redirect.url}")
    private String redirectUrl;

    private final ServerRedirectStrategy serverRedirectStrategy = new DefaultServerRedirectStrategy();
    private final AuthInfoService authInfoService;
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomOauth2LoginSuccessHandler(AuthInfoService authInfoService, JwtUtils jwtUtils) {
        this.authInfoService = authInfoService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        try {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2UserInfo oAuth2UserInfo = (OAuth2UserInfo) oAuth2AuthenticationToken.getPrincipal();
            return authInfoService.registerAuthInfo(oAuth2UserInfo).doOnNext(authInfo -> {
                String token = jwtUtils.generateToken(authInfo);
                jwtUtils.addCookieAccessTokenToResponse(
                        webFilterExchange.getExchange().getResponse(), token
                );
            }).then(serverRedirectStrategy.sendRedirect(webFilterExchange.getExchange(),
                    URI.create(webServerUrl))
            );
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return serverRedirectStrategy.sendRedirect(webFilterExchange.getExchange(),
                URI.create(webServerUrl));
    }
}
