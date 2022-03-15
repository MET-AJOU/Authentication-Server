package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.AuthInfo;
import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.repository.AuthInfoRepository;
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
    private final AuthInfoRepository authInfoRepository;

    @Autowired
    public TokenService(JwtUtil jwtUtil, AuthInfoRepository authInfoRepository) {
        this.jwtUtil = jwtUtil;
        this.authInfoRepository = authInfoRepository;
    }

    public Mono<Void> refreshAccessTokenInCookie(CustomUser user, ServerHttpResponse response) {
        try {
            return authInfoRepository.findAuthInfoByUserCode(user.getUserCode())
                    .doOnNext(authInfo -> {
                        String token = jwtUtil.generateToken(authInfo);
                        response.addCookie(jwtUtil.makeAddingResponseCookieAccessToken(token));
                    }).then(Mono.empty());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Mono.empty();
    }

    public Mono<Date> getExpiredTime(CustomUser user) {
        return Mono.just(jwtUtil.getExpirationDateFromToken(user.getAccessToken()));
    }

    public void deleteAccessTokenInCookie(ServerHttpResponse response) {
        response.addCookie(jwtUtil.makeDeletingResponseCookieAccessToken());
        return;
    }

}
