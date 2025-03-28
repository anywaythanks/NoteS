package com.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccountForbiddenException extends ForbiddenException {
   public AccountForbiddenException() {
      this("Account access Denied.");
   }

   public AccountForbiddenException(String message) {
      super(message);
   }
}
