package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleAckEvent extends ScheduleEvent {
   private SimpleAcknowledge acknowledgment;

   public ScheduleAckEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId, SagaEvent eventType, SimpleAcknowledge acknowledgment) {
      super(sagaId, noteId, oldCommitId, newCommitId, eventType);
      this.acknowledgment = acknowledgment;
   }
}
