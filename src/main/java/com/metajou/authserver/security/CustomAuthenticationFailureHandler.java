package com.metajou.authserver.security;

import com.metajou.authserver.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.beans.ConstructorProperties;

@Component
public class CustomAuthenticationFailureHandler extends RedirectServerAuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler() {
        super("/login/err?");
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.error(ExceptionCode.AUTH_TOKEN_ERROR.build());
    }
}
