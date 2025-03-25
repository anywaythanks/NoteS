package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountException extends RuntimeException {
    public AccountException(String message) {
        super(message);
    }
}
