package com.metajou.authserver.exception;

import com.metajou.authserver.exception.custom.GlobalException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        map.remove("error");
        map.remove("path");

        Throwable throwable = getError(request);

        if (throwable instanceof GlobalException) {
            GlobalException ex = (GlobalException) getError(request);
            map.put("res", null);
            map.put("message", ex.getReason());
            map.put("status", ex.getStatus().value());
            return map;
        }

        map.put("res", "");
        map.put("message", throwable.getMessage());
        map.put("status", 500);
        return map;
    }

}