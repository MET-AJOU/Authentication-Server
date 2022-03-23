package com.metajou.authserver.entity.token;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@ApiModel(value = "인증 정보")
public class AuthInfoRes {
    private String user;
    private String provider;
    private Date expiredTime;
    private String role;
    private String verifiedEmail;
}
