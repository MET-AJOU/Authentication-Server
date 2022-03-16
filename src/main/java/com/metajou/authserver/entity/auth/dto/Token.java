package com.metajou.authserver.entity.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;

@Getter
@AllArgsConstructor
public class Token {
    private final String tokenValue;
}
