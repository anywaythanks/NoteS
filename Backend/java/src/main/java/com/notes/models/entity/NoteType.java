package com.notes.models.entity;

import lombok.Getter;

@Getter
public enum NoteType {
   NOTE(0),
   COMMENT(1),
   COMMENT_REDACTED(2);
   final int id;

   NoteType(int id) {
      this.id = id;
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
