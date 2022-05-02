package com.metajou.authserver.entity.verify.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VerifyInfoResult
{
    private String verifyEmail;
    private LocalDateTime verifyTime;
    @Builder.Default
    private Boolean useable = false;
}
