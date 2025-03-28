package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public abstract class ApplicationException extends RuntimeException {
   public ApplicationException() {
      this("Bad Request");
   }

   public ApplicationException(String message) {
      super(message);
   }
}
