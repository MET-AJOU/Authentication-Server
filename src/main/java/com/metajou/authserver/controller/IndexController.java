package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@RestController
public class IndexController {

    private final AuthInfoService authInfoService;
    private final TokenService tokenService;

    @Autowired
    public IndexController(AuthInfoService authInfoService, TokenService tokenService) {
        this.authInfoService = authInfoService;
        this.tokenService = tokenService;
    }

    @GetMapping()
    public Mono<String> getHome() {
        return Mono.just("Hello World!");
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<Void> getLogIn(@PathVariable String id, ServerHttpResponse response) {
        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/oauth2/authorization/" + id))
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> getLogOut(@AuthenticationPrincipal CustomUser user, ServerHttpResponse response) {
        tokenService.deleteAccessTokenInCookie(response);
        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/"))
                .build();
    }
}
