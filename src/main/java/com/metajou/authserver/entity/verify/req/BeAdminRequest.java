package com.metajou.authserver.entity.verify.req;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@NoArgsConstructor
@Jacksonized
public class BeAdminRequest {
    @JsonProperty
    private String serverRootToken;
}
