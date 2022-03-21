package com.metajou.authserver.controller;

import com.metajou.authserver.entity.CustomResponse;
import com.metajou.authserver.entity.auth.dto.Token;
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

    private final AuthInfoService authInfoService;
    private final TokenService tokenService;

    @Autowired
    public MainController(AuthInfoService authInfoService, TokenService tokenService) {
        this.authInfoService = authInfoService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Mono<ResponseEntity<Void>> getHome() {
        return Mono.error(ExceptionCode.NOT_FOUND_404.build());
    }

    ///oauth2/authorization/google <= login

    @GetMapping("/api")
    public Mono<ResponseEntity<Void>> getSwaggerHome() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/swagger-ui/index.html"))
                .build());
    }

    @GetMapping("/testresponse")
    public Mono<ResponseEntity<CustomResponse>> testResApi() {
        return Mono.just(ResponseEntity.ok()
                .body(
                        new CustomResponse(new Token("mimimi"))
                                .injectExceptionCode(ExceptionCode.NOT_FOUND_404)
                                .build()
                ));
    }

    @GetMapping("/testerror")
    public Mono<ResponseEntity<CustomResponse>> testErrorApi() {
        return Mono.error(ExceptionCode.NO_VERIFIED_USER.build());
    }
}
