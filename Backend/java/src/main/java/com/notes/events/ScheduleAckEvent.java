package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ScheduleAckEvent extends ScheduleEvent {
   private final SimpleAcknowledge acknowledgment;

   public ScheduleAckEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, Instant createdAt, SagaEvent eventType, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, createdAt, eventType);
      this.acknowledgment = acknowledgment;
   }
}
