package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends NotFoundException {
   public NoteNotFoundException() {
      this("Note not Found.");
   }

   public NoteNotFoundException(String message) {
      super(message);
   }
}
