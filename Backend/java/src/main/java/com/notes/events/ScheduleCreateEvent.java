package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public final class ScheduleCreateEvent extends ScheduleEvent {
   public ScheduleCreateEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, Instant createdAt) {
      super(sagaId, noteId, oldCommitId, newCommitId, createdAt, SagaEvent.CREATE);
   }
}
