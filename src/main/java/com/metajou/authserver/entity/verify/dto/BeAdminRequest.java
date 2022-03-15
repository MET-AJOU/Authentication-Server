package com.metajou.authserver.entity.verify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BeAdminRequest {
    private final String serverRootToken;

    public BeAdminRequest(@JsonProperty("serverRootToken") String serverRootToken) {
        this.serverRootToken = serverRootToken;
    }
}
