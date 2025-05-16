package com.notes.models.entity;

import lombok.Getter;

@Getter
public enum EntryType {
   NOTE(0, "NOTE"),
   COMMENT(1, "COMMENT");
   final int id;
   final String name;

   EntryType(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public static EntryType valueOf(int i) {
      return switch(i) {
         case 0 -> NOTE;
         case 1 -> COMMENT;
         default -> throw new IllegalArgumentException("Unknown note type: " + i);
      };
   }
}
