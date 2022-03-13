package com.metajou.authserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    @GetMapping()
    public Mono<String> getHome() {
        return Mono.just("Hello World!");
    }
}
