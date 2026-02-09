package com.funfair.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotValidInputException extends RuntimeException {

    private static String InvalidInput;

    private String message;

    @Autowired
    public void GetPropertiesBean(@Value("${error.message.invalid.input}") String InvalidInput) {
        NotValidInputException.InvalidInput = InvalidInput;
        System.out.println(InvalidInput);
    }

    public NotValidInputException(String message) {
        super(message);
        this.message = message;
    }

    public NotValidInputException() {
        super(InvalidInput);
    }

}
