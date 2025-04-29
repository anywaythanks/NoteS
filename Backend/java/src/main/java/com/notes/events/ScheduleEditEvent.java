package com.notes.events;

import com.notes.models.entity.SagaEvent;

import java.time.Instant;
import java.util.UUID;

public class ScheduleEditEvent extends ScheduleEvent {
   public ScheduleEditEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, Instant createdAt) {
      super(sagaId, noteId, oldCommitId, newCommitId, createdAt, SagaEvent.MODIFICATE);
   }
}
