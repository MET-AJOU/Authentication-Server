package com.metajou.authserver.entity.verify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AjouEmailVerifyRequest {
    private final String userId;
}
