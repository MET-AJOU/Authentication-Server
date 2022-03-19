package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.auth.dto.Token;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final AuthInfoService authInfoService;
    private final TokenService tokenService;

    @Autowired
    public TokenController(AuthInfoService authInfoService, TokenService tokenService) {
        this.authInfoService = authInfoService;
        this.tokenService = tokenService;
    }

    @GetMapping()
    public Mono<ResponseEntity<String>> getTokenHome() {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Hello Token!"));
    }

    @GetMapping("/expiredtime")
    public Mono<ResponseEntity<Date>> getTokenExpireTime(@AuthenticationPrincipal CustomUser user) {
        return tokenService.getExpiredTime(user).map(date -> {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(date);
        });
    }

    @GetMapping("/mine")
    public Mono<ResponseEntity<Token>> getUserAuthInfo(@AuthenticationPrincipal CustomUser user) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new Token(user.getToken().getTokenValue())));
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<Token>> getRefreshToken(@AuthenticationPrincipal CustomUser user, ServerHttpResponse response) {
        Mono<Token> refreshMono = tokenService.refreshAccessTokenInCookie(user, response);
        return refreshMono.map(token ->
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(token)
        );
    }

}
