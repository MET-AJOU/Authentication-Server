package com.metajou.authserver.security;

import com.metajou.authserver.entity.oauth2.OAuth2UserInfo;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomOauth2LoginSuccessHandler extends RedirectServerAuthenticationSuccessHandler {

    private final AuthInfoService authInfoService;
    private final JwtUtil jwtUtil;

    @Autowired
    public CustomOauth2LoginSuccessHandler(AuthInfoService authInfoService, JwtUtil jwtUtil) {
        this.authInfoService = authInfoService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        Mono<Void> delegate = super.onAuthenticationSuccess(webFilterExchange, authentication);

        try {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            OAuth2UserInfo oAuth2UserInfo = (OAuth2UserInfo) oAuth2AuthenticationToken.getPrincipal();

            return authInfoService.registerAuthInfo(oAuth2UserInfo).doOnNext(authInfo -> {
                String token = jwtUtil.generateToken(authInfo);
                webFilterExchange.getExchange().getResponse()
                        .addCookie(
                                jwtUtil.makeAddingResponseCookieAccessToken(token)
                        );
            }).then(delegate);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return delegate;
    }
}
