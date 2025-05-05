package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class ErrorSync extends ScheduleAckEvent {
   public ErrorSync(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SagaEvent eventType, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, eventType, acknowledgment);
   }
}
