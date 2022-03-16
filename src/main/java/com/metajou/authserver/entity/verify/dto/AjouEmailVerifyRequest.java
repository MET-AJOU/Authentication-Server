package com.metajou.authserver.entity.verify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AjouEmailVerifyRequest {
    private final String ajouUserId;

    public AjouEmailVerifyRequest(@JsonProperty("AjouUserId") String ajouUserId) {
        this.ajouUserId = ajouUserId;
    }
}
