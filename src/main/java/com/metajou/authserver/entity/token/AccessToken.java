package com.metajou.authserver.entity.token;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema
public class AccessToken {
    @ApiModelProperty(value = "권한이 필요한 API에 access를 하기 위한 token 값.")
    private String accessToken;
}
