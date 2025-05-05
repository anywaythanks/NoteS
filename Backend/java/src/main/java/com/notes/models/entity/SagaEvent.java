package com.notes.models.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum SagaEvent {
   SCHEDULED_INDEX(0),
   SCHEDULED_REINDEX(1),
   SCHEDULED_REMOVE_INDEX(2),
   SUCCESS_CREATED(3),
   SUCCESS_EDITED(4),
   SUCCESS_DELETED(5),
   ERROR_CREATED(6),
   ERROR_EDITED(7),
   ERROR_DELETED(8),
   FAILED(9),
   COMPENSATE(10);
   final int id;
   final static Map<Integer, SagaEvent> map = Arrays.stream(SagaEvent.values())
           .collect(Collectors.toMap(SagaEvent::getId, Function.identity()));

   SagaEvent(int id) {
      this.id = id;
   }

   public static SagaEvent valueOf(int i) {
      return map.get(i);
   }
}
