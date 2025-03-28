package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TagUniqueException extends UniqueException {
   public TagUniqueException() {
      this("Tag already exists.");
   }

   public TagUniqueException(String message) {
      super(message);
   }
}
