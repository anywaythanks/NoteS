package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UniqueException extends RuntimeException {
    public UniqueException() {
        this("Already exists.");
    }

    public UniqueException(String message) {
        super(message);
    }
}
