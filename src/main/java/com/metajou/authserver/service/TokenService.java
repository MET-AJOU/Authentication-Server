package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

import java.util.Date;

@Service
public class TokenService {

    private final JwtUtil jwtUtil;

    @Autowired
    public TokenService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Mono<Date> getExpiredTime(CustomUser user) {
        return Mono.just(jwtUtil.getExpirationDateFromToken(user.getAccessToken()));
    }

    public void deleteAccessTokenInCookie(ServerHttpResponse response) {
        response.addCookie(jwtUtil.makeDeletingResponseCookieAccessToken());
        return;
    }
}
