package com.notes.events;

import com.notes.models.entity.SagaEvent;

import java.util.UUID;

public class SyncEditEvent extends AckEvent {
   public SyncEditEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, acknowledgment);
   }
}
