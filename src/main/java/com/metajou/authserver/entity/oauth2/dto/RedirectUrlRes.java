package com.metajou.authserver.entity.oauth2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectUrlRes {
    private String redirectUrl;

    public RedirectUrlRes(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
