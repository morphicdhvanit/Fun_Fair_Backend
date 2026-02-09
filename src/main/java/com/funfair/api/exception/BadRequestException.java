package com.funfair.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)  // Maps exception → 400 error
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}