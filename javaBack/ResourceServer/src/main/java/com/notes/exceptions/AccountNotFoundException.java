package com.notes.exceptions;

public class AccountNotFoundException extends NotFoundException {
   public AccountNotFoundException() {
      this("Account not Found.");
   }

   public AccountNotFoundException(String message) {
      super(message);
   }
}
