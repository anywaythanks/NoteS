package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class BaseEvent {
   private final UUID sagaId;
   private final Long noteId;
   private final Long oldCommitId;
   private final Long newCommitId;
   private final SagaEvent eventType;
}
