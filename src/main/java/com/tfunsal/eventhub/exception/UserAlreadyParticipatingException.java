package com.tfunsal.eventhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyParticipatingException extends RuntimeException{
    public UserAlreadyParticipatingException(String message) {
        super(message);
    }
}
