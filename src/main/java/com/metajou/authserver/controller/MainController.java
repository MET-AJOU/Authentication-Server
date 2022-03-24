package com.metajou.authserver.controller;

import com.metajou.authserver.entity.response.BaseResponse;
import com.metajou.authserver.entity.response.ResponseWrapper;
import com.metajou.authserver.entity.Token;
import com.metajou.authserver.exception.ExceptionCode;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class MainController {

    @GetMapping("/login/google")
    public Mono<ResponseEntity> getGoogleLogin() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/oauth2/authorization/google"))
                .build());
    }

    @GetMapping("/api")
    public Mono<ResponseEntity> getSwaggerHome() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/swagger-ui/index.html"))
                .build());
    }
}
