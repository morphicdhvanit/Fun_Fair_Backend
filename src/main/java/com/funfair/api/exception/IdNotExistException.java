package com.funfair.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Component
public class IdNotExistException extends RuntimeException {

    private static String notExist;

    @Autowired
    public void GetPropertiesBean(@Value("${error.message.wrong.id}") String notExist) {
        IdNotExistException.notExist = notExist;
        System.out.println(notExist);
    }

    private String message;

    public IdNotExistException(String message) {
        super(message);
        this.message = message;
    }

    public IdNotExistException() {
        super(notExist);
    }

}