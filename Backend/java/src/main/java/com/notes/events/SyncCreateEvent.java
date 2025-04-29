package com.notes.events;

import com.notes.models.entity.SagaEvent;

import java.time.Instant;
import java.util.UUID;

public class SyncCreateEvent extends AckEvent {
   public SyncCreateEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, SagaEvent.CREATE, acknowledgment);
   }
}
