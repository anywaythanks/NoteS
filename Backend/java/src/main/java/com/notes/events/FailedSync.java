package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
public class FailedSync extends ScheduleAckEvent {
   public FailedSync(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, SagaEvent.FAILED, acknowledgment);
   }
}
