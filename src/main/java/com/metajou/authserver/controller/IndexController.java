package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.AuthInfo;
import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.service.AuthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    private final AuthInfoService authInfoService;

    @Autowired
    public IndexController(AuthInfoService authInfoService) {
        this.authInfoService = authInfoService;
    }

    @GetMapping()
    public Mono<String> getHome() {
        return Mono.just("Hello World!");
    }

    @GetMapping("/logout")
    public Mono<String> getLogOut(@AuthenticationPrincipal CustomUser user) {
        return Mono.empty();
    }
}
