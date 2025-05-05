package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class ScheduleEditEvent extends ScheduleEvent {
   public ScheduleEditEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId) {
      super(sagaId, noteId, oldCommitId, newCommitId, SagaEvent.SCHEDULED_REINDEX);
   }
}
