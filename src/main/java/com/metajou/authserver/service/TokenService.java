package com.metajou.authserver.service;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.auth.dto.Token;
import com.metajou.authserver.repository.AuthInfoRepository;
import com.metajou.authserver.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TokenService {

    private final JwtUtils jwtUtils;
    private final AuthInfoRepository authInfoRepository;

    @Autowired
    public TokenService(JwtUtils jwtUtils, AuthInfoRepository authInfoRepository) {
        this.jwtUtils = jwtUtils;
        this.authInfoRepository = authInfoRepository;
    }

    public Mono<Token> refreshAccessTokenInCookie(CustomUser user, ServerHttpResponse response) {
        try {
            return authInfoRepository.findAuthInfoByUserCode(user.getUserCode())
                    .map(authInfo -> {
                        String token = jwtUtils.generateToken(authInfo);
                        jwtUtils.addCookieAccessTokenToResponse(
                                response,
                                token
                        );
                        return new Token(token);
                    });
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Mono.empty();
    }

    public Mono<Date> getExpiredTime(CustomUser user) {
        return Mono.just(jwtUtils.getExpirationDateFromToken(user.getToken().getTokenValue()));
    }

    public void deleteAccessTokenInCookie(ServerHttpResponse response) {
        response.addCookie(jwtUtils.makeDeletingResponseCookieAccessToken());
        return;
    }

}
