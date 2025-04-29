package com.notes.models.entity;

import lombok.Getter;

@Getter
public enum SagaEvent {
   CREATE(0),
   MODIFICATE(1),
   DELETE(2),
   FAILED(3),
   SUCCESS(4),
   COMPENSATE(5);
   final int id;

   SagaEvent(int id) {
      this.id = id;
   }

   public static SagaEvent valueOf(int i) {
      return switch(i) {
         case 0 -> CREATE;
         case 1 -> MODIFICATE;
         case 2 -> DELETE;
         case 3 -> FAILED;
         case 4 -> SUCCESS;
         case 5 -> COMPENSATE;
         default -> throw new IllegalArgumentException("Unknown saga event: " + i);
      };
   }
}
