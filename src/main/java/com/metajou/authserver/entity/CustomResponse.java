package com.metajou.authserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CustomResponse <T> {
    T res;
    String message;
    Integer httpStatus;
}
