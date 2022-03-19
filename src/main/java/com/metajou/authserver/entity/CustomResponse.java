package com.metajou.authserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomResponse <T> {
    T res;
    String message;
    Integer httpStatus;
}
