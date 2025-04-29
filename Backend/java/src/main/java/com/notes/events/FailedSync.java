package com.notes.events;

import com.notes.models.entity.SagaEvent;

import java.time.Instant;
import java.util.UUID;

public class FailedSync extends ScheduleAckEvent {
   public FailedSync(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, Instant createdAt, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, createdAt, SagaEvent.FAILED, acknowledgment);
   }
}
