package com.metajou.authserver.entity.verify.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class VerifyTokenRequest {
    private final String verifyToken;

    public VerifyTokenRequest(@JsonProperty("VerifyToken") String verifyToken) {
        this.verifyToken = verifyToken;
    }
}
