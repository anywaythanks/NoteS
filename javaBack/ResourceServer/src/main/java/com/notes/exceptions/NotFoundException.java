package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends ApplicationException {
   public NotFoundException() {
      this("Not Found.");
   }

   public NotFoundException(String message) {
      super(message);
   }
}
