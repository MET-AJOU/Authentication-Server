package com.metajou.authserver.entity.token;

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
}
