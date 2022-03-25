package com.metajou.authserver.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metajou.authserver.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;

@Builder
@AllArgsConstructor
public class BaseResponse {

    @JsonProperty(value = "res")
    @Qualifier("res")
    private Object body;
    @Builder.Default @NonNull
    private final HttpStatus status = HttpStatus.OK;
    @Builder.Default @NonNull
    private final MediaType contentType = MediaType.APPLICATION_JSON;
    @Builder.Default
    private final ExceptionCode except = null;

    public Mono<ResponseEntity> toMonoEntity() {
        if(body instanceof Mono<?>)
            return extractMonoData();
        return Mono.just(this.toEntity());
    }

    public ResponseEntity toEntity() {
        if(except == null) {
            return ResponseEntity.status(this.status)
                    .contentType(contentType)
                    .body(new ResponseWrapper(body));
        }
        return ResponseEntity.status(except.build().getStatus())
                .contentType(contentType)
                .body(new ResponseWrapper(body, except));
    }

    private Mono<ResponseEntity> extractMonoData() {
        return  ((Mono<?>) body).map(o -> {
            this.body = o;
            return toEntity();
        });
    }
}
