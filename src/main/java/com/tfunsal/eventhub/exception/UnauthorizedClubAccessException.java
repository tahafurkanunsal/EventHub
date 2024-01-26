package com.tfunsal.eventhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedClubAccessException extends RuntimeException {
    public UnauthorizedClubAccessException(String message) {
        super(message);
    }
}
