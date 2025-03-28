package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NoteTypeException extends ApplicationException {
   public NoteTypeException() {
      this("The entry type does not match.");
   }

   public NoteTypeException(String message) {
      super(message);
   }
}
