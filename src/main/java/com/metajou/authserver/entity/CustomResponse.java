package com.metajou.authserver.entity;

import com.metajou.authserver.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;

@Getter
@AllArgsConstructor
public class CustomResponse <T> {

    protected HttpStatus status = HttpStatus.OK;
    private Boolean state = true;
    protected String message = "request is successed";
    protected T res;

    public CustomResponse(T tmp) {
        res = tmp;
    }

    public CustomResponse injectExceptionCode(ExceptionCode code) {
        message = code.build().getReason();
        status = code.build().getStatus();
        return this;
    }

    public CustomResponse injectResponseCode(/*TODO Response Code like injectExceptionCode()*/) {
        //TODO Code
        return this;
    }

    public CustomResponse build() {
        return this;
    }


}
