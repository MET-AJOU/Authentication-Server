package com.metajou.authserver.entity.response;

import com.metajou.authserver.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> {

    @Setter
    protected Integer status = HttpStatus.OK.value();
    @Setter
    private Boolean state = true;
    @Setter
    protected String message = "request is successed";
    @Setter
    protected T res;

    public ResponseWrapper(T obj){
        this.res = obj;
    };

    public ResponseWrapper(T obj, ExceptionCode code) {
        this.res = obj;
        this.state = true;
        this.message = code.build().getReason();
        this.status = code.build().getStatus().value();
    }

    public ResponseWrapper(ExceptionCode code) {
        this.res = null;
        this.state = false;
        this.message = code.build().getReason();
        this.status = code.build().getStatus().value();
    }

}
