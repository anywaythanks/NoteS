package com.notes.events;

import com.notes.models.entity.SagaEvent;

import java.util.UUID;

public class CompensateEvent extends AckEvent {
   public CompensateEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, acknowledgment);
   }
}
