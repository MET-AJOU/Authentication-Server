package com.metajou.authserver.entity.token;

import com.metajou.authserver.controller.TokenController;
import com.metajou.authserver.entity.response.ResponseWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class AuthInfoRes {
    private String user;
    private String provider;
    private Date expiredTime;
    private String role;
    private String verifiedEmail;
    private Boolean useable;
}

