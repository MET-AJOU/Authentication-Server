package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.verify.dto.AjouEmailVerifyRequest;
import com.metajou.authserver.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {

    private final VerifyService verifyService;

    @Autowired
    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @GetMapping("/ajouemail")
    public Mono<ResponseEntity<String>> getVerifyAjouEmail(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody AjouEmailVerifyRequest reqData
    ) {
        return Mono.just(ResponseEntity.ok(reqData.getUserId()));
    }

}
