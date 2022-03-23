package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.response.BaseResponse;
import com.metajou.authserver.entity.token.AuthInfoRes;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity> getToken(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(user.getToken())
                .build().toMonoEntity();
    }

    @GetMapping("/mine")
    @ApiOperation(value = "현재 나의 인증 정보를 불러옵니다.", notes = "인증이 필요함.")
    public Mono<ResponseEntity> getUserAuthInfo(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(tokenService.getSessionAuthInfo(user))
                .build().toMonoEntity();
    }

    @GetMapping("/refresh")
    @ApiOperation(value = "쿠키에 저장된 서비스 AccessToken을 재발급하여 저장합니다.", notes = "인증이 필요함.")
    public Mono<ResponseEntity> getRefreshToken(@AuthenticationPrincipal CustomUser user, ServerHttpResponse response) {
        return BaseResponse.builder()
                .body(tokenService.refreshAccessTokenInCookie(user, response))
                .build().toMonoEntity();
    }

}
