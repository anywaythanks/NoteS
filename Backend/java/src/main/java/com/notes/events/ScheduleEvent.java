package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Именно этот эвент перехватывают для логирования, следовательно, наследуясь от него ты говоришь, что мне нужны сага логи.
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class ScheduleEvent extends BaseEvent {
   private SagaEvent eventType;

   public ScheduleEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SagaEvent eventType) {
      super(sagaId, noteId, oldCommitId, newCommitId);
      this.eventType = eventType;
   }
}
