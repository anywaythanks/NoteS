package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends ApplicationException {
   public ForbiddenException() {
      this("Access Denied.");
   }

   public ForbiddenException(String message) {
      super(message);
   }
}
