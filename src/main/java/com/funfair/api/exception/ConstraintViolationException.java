package com.funfair.api.exception;

public class ConstraintViolationException extends RuntimeException {
    private String message;

    public ConstraintViolationException(String message) {
        super(message);
        this.message = message;
    }

    public ConstraintViolationException() {
    }

}
