package com.metajou.authserver.exception;

import com.metajou.authserver.exception.custom.GlobalException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ExceptionCode {

    //JWT TOKEn ERROR
    AUTH_TOKEN_ERROR(new GlobalException(HttpStatus.BAD_REQUEST, "접근 토근이 유효하지 않습니다.")),
    //Auth
    NOT_FOUND_AUTHINFO(new GlobalException(HttpStatus.NOT_FOUND, "인증정보가 존재하지 않습니다.")),
    NO_VERIFIED_USER(new GlobalException(HttpStatus.UNAUTHORIZED, "인증받지 않은 유저입니다.")),
    EXPIRED_TOKEN(new GlobalException(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.")),
    AUTH_SERVER_ERROR(new GlobalException(HttpStatus.BAD_REQUEST, "인증 서버에 문제가 발생했습니다")),
    //Verify
    NON_FOUND_VERIFYTOKEN(new GlobalException(HttpStatus.BAD_REQUEST, "유저에게 적합한 이메일 인증 정보가 존재하지 않습니다.")),
    CANT_CREATE_VERIFY_INFO(new GlobalException(HttpStatus.BAD_REQUEST, "인증 정보 추가에 실패했습니다.")),
    CANT_REMOVE_VERIFINGTOKENINFO(new GlobalException(HttpStatus.BAD_REQUEST, "임시 이메일 인증 토큰을 제거하는데 문제가 발생했습니다.")),
    ERROR_GET_VERIFYING_TOKEN(new GlobalException(HttpStatus.BAD_REQUEST, "인증 토큰을 불러올 수 없습니다")),

    ALREADY_EXIST_VERIFYINFO(new GlobalException(HttpStatus.ALREADY_REPORTED, "이미 인증된 유저입니다")),

    //Global
    NOT_FOUND_404(new GlobalException(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."));


    private final GlobalException globalException;

    public GlobalException build() {
        return globalException;
    }
}
