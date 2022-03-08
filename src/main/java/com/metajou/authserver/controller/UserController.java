package com.metajou.authserver.controller;

import com.metajou.authserver.entity.oauth.OAuth2UserInfo;
import com.metajou.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/currentuser")
    public Mono<Object> GetUser(@AuthenticationPrincipal Mono<OAuth2UserInfo> userMono) {
        return userMono.map(userInfo -> userInfo);
    }
}
