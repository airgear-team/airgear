package com.airgear.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GoodsNotFoundException extends RuntimeException {

    public GoodsNotFoundException(String message) {
        super(message);
    }
}
