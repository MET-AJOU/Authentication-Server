package com.metajou.authentication.controller;

import com.metajou.authentication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountService service;
    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
