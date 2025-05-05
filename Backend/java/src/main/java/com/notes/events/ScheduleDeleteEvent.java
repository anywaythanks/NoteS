package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class ScheduleDeleteEvent extends ScheduleEvent {
   public ScheduleDeleteEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId) {
      super(sagaId, noteId, oldCommitId, newCommitId, SagaEvent.SCHEDULED_REMOVE_INDEX);
   }
}
