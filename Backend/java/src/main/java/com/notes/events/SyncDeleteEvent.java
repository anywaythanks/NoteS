package com.notes.events;

import java.util.UUID;

public class SyncDeleteEvent extends AckEvent {
   public SyncDeleteEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, acknowledgment);
   }
}
