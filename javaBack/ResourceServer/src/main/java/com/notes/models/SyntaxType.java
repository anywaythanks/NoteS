package com.notes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SyntaxType {
   @JsonProperty("PLAINTEXT")
   PLAINTEXT(0, "PLAINTEXT"),
   @JsonProperty("MARKDOWN")
   MARKDOWN(1, "MARKDOWN");
   final int id;
   final String name;

   SyntaxType(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public static SyntaxType valueOf(int i) {
      return switch (i) {
         case 0 -> PLAINTEXT;
         case 1 -> MARKDOWN;
         default -> throw new IllegalArgumentException("Unknown syntax type: " + i);
      };
   }
}
