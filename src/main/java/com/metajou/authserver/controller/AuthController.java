package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthInfoService authInfoService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthInfoService authInfoService, TokenService tokenService) {
        this.authInfoService = authInfoService;
        this.tokenService = tokenService;
    }

    @GetMapping()
    public Mono<String> getApiHome() {
        return Mono.just("Hello Api!");
    }

    @GetMapping("/mytokentime")
    public Mono<Date> getTokenExpireTime(@AuthenticationPrincipal CustomUser user) {
        return tokenService.getExpiredTime(user);
    }

    @GetMapping("/mytoken")
    public Mono<String> getUserAuthInfo(@AuthenticationPrincipal CustomUser user) {
        return Mono.just(user.getAccessToken());
    }

}
