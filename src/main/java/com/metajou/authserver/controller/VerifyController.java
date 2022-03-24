package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.auth.Role;
import com.metajou.authserver.entity.response.BaseResponse;
import com.metajou.authserver.entity.verify.req.AjouEmailVerifyRequest;
import com.metajou.authserver.entity.verify.req.VerifyTokenRequest;
import com.metajou.authserver.entity.verify.res.VerifingTokenSendResult;
import com.metajou.authserver.service.AuthInfoService;
import com.metajou.authserver.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {
    private final VerifyService verifyService;
    private final AuthInfoService authInfoService;

    @Autowired
    public VerifyController(VerifyService verifyService, AuthInfoService authInfoService) {
        this.verifyService = verifyService;
        this.authInfoService = authInfoService;
    }

    @PostMapping()
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

    @PostMapping("/send/ajouemail")
    public Mono<ResponseEntity> getVerifyAjouEmail(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody AjouEmailVerifyRequest reqData
    ) {
        return BaseResponse.builder()
                .body(verifyService.sendVerifyTokenToAjouEmail(user, reqData))
                .build().toMonoEntity();
    }

}
