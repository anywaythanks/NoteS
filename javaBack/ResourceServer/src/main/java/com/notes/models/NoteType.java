package com.notes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum NoteType {
   @JsonProperty("NOTE")
   NOTE(0, "NOTE"),
   @JsonProperty("COMMENT")
   COMMENT(1, "COMMENT"),
   @JsonProperty("COMMENT_REDACTED")
   COMMENT_REDACTED(2, "COMMENT_REDACTED");
   final int id;
   final String name;

   NoteType(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public static NoteType valueOf(int i) {
      return switch (i) {
         case 0 -> NOTE;
         case 1 -> COMMENT;
         case 2 -> COMMENT_REDACTED;
         default -> throw new IllegalArgumentException("Unknown note type: " + i);
      };
   }
}
