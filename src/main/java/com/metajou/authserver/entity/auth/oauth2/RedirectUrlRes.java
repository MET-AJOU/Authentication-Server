package com.metajou.authserver.entity.auth.oauth2;

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
