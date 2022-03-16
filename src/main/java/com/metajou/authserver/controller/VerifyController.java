package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.verify.dto.AjouEmailVerifyRequest;
import com.metajou.authserver.entity.verify.dto.VerifyTokenRequest;
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

    @Autowired
    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @PostMapping()
    public Mono<ResponseEntity<String>> makeVerify(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody VerifyTokenRequest reqData
            ) {
        return verifyService.checkVerifyTokenIsCorrect(user, reqData)
                .map(boolData -> {
                    if(boolData)
                        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Successed");
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Failed");
                });
    }

    @GetMapping("/send/ajouemail")
    public Mono<ResponseEntity<String>> getVerifyAjouEmail(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody AjouEmailVerifyRequest reqData
    ) {
        return verifyService.sendVerifyTokenToAjouEmail(user, reqData)
                .map(boolData -> {
                    if(boolData) {
                        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                                .body("email is sended!");
                    }
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                            .body("email is not sended!");
                });
    }

}
