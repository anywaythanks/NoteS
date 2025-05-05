package com.notes.models.entity;

import lombok.Getter;

@Getter
public enum NoteType {
   NOTE(0, "note"),
   COMMENT(1, "comment"),
   COMMENT_REDACTED(2, "comment_redacted");
   final int id;
   final String name;

   NoteType(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public static NoteType valueOf(int i) {
      return switch(i) {
         case 0 -> NOTE;
         case 1 -> COMMENT;
         case 2 -> COMMENT_REDACTED;
         default -> throw new IllegalArgumentException("Unknown note type: " + i);
      };
   }
}
