package com.metajou.authserver.entity.verify.req;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@NoArgsConstructor
@Jacksonized
public class VerifyTokenRequest {
    @JsonProperty
    private String verifyToken;
}
