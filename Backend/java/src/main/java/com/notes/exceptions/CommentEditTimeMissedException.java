package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CommentEditTimeMissedException extends ApplicationException {
   public CommentEditTimeMissedException() {
      this("Time to edit comment expired.");
   }

   public CommentEditTimeMissedException(String message) {
      super(message);
   }
}
