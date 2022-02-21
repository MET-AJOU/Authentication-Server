package com.metajou.authentication.controller;

import com.metajou.authentication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    private final AccountService service;
    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Mono<String> index(@AuthenticationPrincipal Mono<OAuth2User> oauth2User) {
        if(oauth2User == null) {
            return Mono.just("로그인이 필요함.");
        }
        return oauth2User.map(OAuth2User::getAttributes).map(name -> String.format("Hi, %s", name));
    }

}
