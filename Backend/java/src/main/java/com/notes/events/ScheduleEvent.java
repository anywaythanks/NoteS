package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Именно этот эвент перехватывают для логирования, следовательно, наследуясь от него ты говоришь, что мне нужны сага логи.
 */
@Getter
@Setter
public abstract class ScheduleEvent extends BaseEvent {
   public ScheduleEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, Instant createdAt, SagaEvent eventType) {
      super(sagaId, noteId, oldCommitId, newCommitId, eventType);
   }
}
