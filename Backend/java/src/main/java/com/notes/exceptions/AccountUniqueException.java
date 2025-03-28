package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountUniqueException extends UniqueException {
   public AccountUniqueException() {
      this("Account already exists.");
   }

   public AccountUniqueException(String message) {
      super(message);
   }
}
