package com.metajou.authserver.exception;

import com.metajou.authserver.exception.custom.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    // 인증문제
    NO_VERIFIED_USER(new GlobalException(HttpStatus.UNAUTHORIZED, "인증받지 않은 유저입니다.")),
    EXPIRED_TOKEN(new GlobalException(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.")),

    NOT_FOUND_404(new GlobalException(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."));


    private final GlobalException globalException;
}
