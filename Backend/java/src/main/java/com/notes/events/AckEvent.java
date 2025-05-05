package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Эвент, который еще не был подтвержден
 */
@Getter
@Setter
public abstract class AckEvent extends BaseEvent {
   private final SimpleAcknowledge acknowledgment;

   public AckEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId);
      this.acknowledgment = acknowledgment;
   }
}
