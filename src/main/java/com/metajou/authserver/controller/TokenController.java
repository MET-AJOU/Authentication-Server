package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.response.BaseResponse;
import com.metajou.authserver.entity.response.ResponseWrapper;
import com.metajou.authserver.entity.token.AccessToken;
import com.metajou.authserver.entity.token.AuthInfoRes;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/mine")
    @Operation(summary = "인증된 사용자의 디테일한 인증 정보를 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 정보를 불러옴.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallAuthInfoRes.class))}
            )})
    public Mono<ResponseEntity> getUserAuthInfo(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(tokenService.getSessionAuthInfo(user))
                .build().toMonoEntity();
    }

    @GetMapping("/refresh")
    @Operation(summary = "사용자의 인증정보를 기반으로 새로운 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MSGACCESSTOKEN 쿠키에 값을 넣어줍니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallAccessToken.class))}
            )})
    public Mono<ResponseEntity> getRefreshToken(@AuthenticationPrincipal CustomUser user, ServerHttpResponse response) {
        return BaseResponse.builder()
                .body(tokenService.refreshAccessTokenInCookie(user, response))
                .build().toMonoEntity();
    }

    private class ApiCallAuthInfoRes extends ResponseWrapper<AuthInfoRes> {}
    private class ApiCallAccessToken extends ResponseWrapper<AccessToken> {}
}
