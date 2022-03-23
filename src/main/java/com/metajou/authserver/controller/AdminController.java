package com.metajou.authserver.controller;

import com.metajou.authserver.entity.auth.CustomUser;
import com.metajou.authserver.entity.Token;
import com.metajou.authserver.entity.verify.req.BeAdminRequest;
import com.metajou.authserver.service.AdminService;
import com.metajou.authserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final TokenService tokenService;

    @Autowired
    public AdminController(AdminService adminService, TokenService tokenService) {
        this.adminService = adminService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Mono<ResponseEntity<String>> getAdminHome() {
        return Mono.just(ResponseEntity.ok("Hello Admin!"));
    }

//    @GetMapping("/makemeadmin")
//    public Mono<ResponseEntity<Token>> getMakeMeAdmin(
//            @AuthenticationPrincipal CustomUser user,
//            @RequestBody BeAdminRequest req,
//            ServerHttpResponse response
//    ) {
//        return adminService.beAdmin(user, req)
//                .flatMap(isAvail -> {
//                    if (isAvail)
//                        return tokenService.refreshAccessTokenInCookie(user, response);
//                    else
//                        return Mono.just(user.getToken());
//                }).map(token -> ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(token)
//                );
//    }

}
