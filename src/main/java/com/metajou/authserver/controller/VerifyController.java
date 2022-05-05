package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.auth.Role;
import com.metajou.authserver.entity.response.BaseResponse;
import com.metajou.authserver.entity.response.ResponseWrapper;
import com.metajou.authserver.entity.verify.req.AjouEmailVerifyRequest;
import com.metajou.authserver.entity.verify.req.UpdateUseableRequest;
import com.metajou.authserver.entity.verify.req.VerifyTokenRequest;
import com.metajou.authserver.entity.verify.res.EmailSendResult;
import com.metajou.authserver.entity.verify.res.VerifingTokenSendResult;
import com.metajou.authserver.entity.verify.res.VerifyInfoResult;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.VerifyService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/verify")
@AllArgsConstructor
public class VerifyController {
    private final VerifyService verifyService;
    private final AuthInfoService authInfoService;

    @PostMapping()
    @Operation(summary = "메일로 보낸 인증코드를 검사합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내부 true, false에 따라 전송 성공 여부가 다름.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallVerifingTokenSendResult.class))}
            )})
    public Mono<ResponseEntity> makeVerify(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody VerifyTokenRequest reqData
            ) {
        return BaseResponse.builder()
                .body(verifyService.checkVerifyTokenIsCorrect(user,reqData)
                        .flatMap(bwrapped -> {
                            if(bwrapped.getVerifingTokenSendResult())
                                return authInfoService.updateAuthInfoAuthority(user, Role.ROLE_USER)
                                        .flatMap(abool -> Mono.just(new VerifingTokenSendResult(abool)));
                            return Mono.just(bwrapped);
                        }))
                .build().toMonoEntity();
    }

    @PostMapping("/useable")
    @Operation(summary = "사용자 정보 동의 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallVerifyInfoResult.class))}
            )})
    public Mono<ResponseEntity> updateUseable(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody UpdateUseableRequest reqData
    ) {
        return BaseResponse.builder()
                .body(verifyService.updateVerifyInfo(user,reqData))
                .build().toMonoEntity();
    }

    @PostMapping("/send/ajouemail")
    @Operation(summary = "아주대 계정에 인증코드 메일을 보냅니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내부 true, false에 따라 전송 성공 여부가 다름.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallEmailSendResult.class))}
            )})
    public Mono<ResponseEntity> getVerifyAjouEmail(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody AjouEmailVerifyRequest reqData
    ) {
        return BaseResponse.builder()
                .body(verifyService.sendVerifyTokenToAjouEmail(user, reqData))
                .build().toMonoEntity();
    }


    private class ApiCallEmailSendResult extends ResponseWrapper<EmailSendResult> {}
    private class ApiCallVerifingTokenSendResult extends ResponseWrapper<VerifingTokenSendResult> {}
    private class ApiCallVerifyInfoResult extends ResponseWrapper<VerifyInfoResult> {}

}
