package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NoteForbiddenException extends ForbiddenException {
   public NoteForbiddenException() {
      this("Note access Denied.");
   }

   public NoteForbiddenException(String message) {
      super(message);
   }
}
