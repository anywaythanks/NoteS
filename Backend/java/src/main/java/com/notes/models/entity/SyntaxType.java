package com.notes.models.entity;

import lombok.Getter;

@Getter
public enum SyntaxType {
   PLAINTEXT(0),
   MARKDOWN(1);
   final int id;

   SyntaxType(int id) {
      this.id = id;
   }

   public static SyntaxType valueOf(int i) {
      return switch (i) {
         case 0 -> PLAINTEXT;
         case 1 -> MARKDOWN;
         default -> throw new IllegalArgumentException("Unknown syntax type: " + i);
      };
   }
}
