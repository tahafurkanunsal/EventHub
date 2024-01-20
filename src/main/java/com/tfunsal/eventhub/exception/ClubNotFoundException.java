package com.tfunsal.eventhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClubNotFoundException extends RuntimeException{
    public ClubNotFoundException(String message) {
        super(message);
    }
}
