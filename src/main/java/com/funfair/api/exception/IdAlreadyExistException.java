package com.funfair.api.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdAlreadyExistException extends RuntimeException {

    private static String idAlreadyExist;

    private String message;

    @Autowired
    public void GetPropertiesBean(@Value("${error.message.id.already.exist}") String idAlreadyExist) {
        IdAlreadyExistException.idAlreadyExist = idAlreadyExist;
        System.out.println(idAlreadyExist);
    }

    public IdAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    public IdAlreadyExistException() {
        super(idAlreadyExist);
    }

}
