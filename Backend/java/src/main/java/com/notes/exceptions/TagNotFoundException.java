package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TagNotFoundException extends NotFoundException {
   public TagNotFoundException() {
      this("Tag not Found.");
   }

   public TagNotFoundException(String message) {
      super(message);
   }
}
