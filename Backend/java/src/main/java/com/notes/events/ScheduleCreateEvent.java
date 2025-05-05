package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public final class ScheduleCreateEvent extends ScheduleEvent {
   public ScheduleCreateEvent(UUID sagaId, Long noteId, Long oldCommitId, Long newCommitId) {
      super(sagaId, noteId, oldCommitId, newCommitId, SagaEvent.SCHEDULED_INDEX);
   }
}
