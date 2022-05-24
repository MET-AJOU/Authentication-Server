package com.metajou.authserver.controller;

import com.metajou.authserver.entity.response.BaseResponse;
import com.metajou.authserver.entity.response.ResponseWrapper;
import com.metajou.authserver.entity.Token;
import com.metajou.authserver.exception.ExceptionCode;
import com.metajou.authserver.property.CorsProperties;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final CorsProperties corsProperties;

    @Operation(summary = "구글 로그인 페이지로 리다이렉트합니다.")
    @GetMapping("/login/google")
    public Mono<ResponseEntity> getGoogleLogin() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create(corsProperties.getUrl()[0]+"/oauth2/authorization/google"))
                .build());
    }

    @Operation(summary = "카카오 로그인 페이지로 리다이렉트합니다.")
    @GetMapping("/login/kakao")
    public Mono<ResponseEntity> getKakaoLogin() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create(corsProperties.getUrl()[0]+"/oauth2/authorization/kakao"))
                .build());
    }

    @Operation(summary = "API 홈페이지로 리다이렉트합니다.")
    @GetMapping("/api")
    public Mono<ResponseEntity> getSwaggerHome() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/webjars/swagger-ui/index.html"))
                .build());
    }
}
