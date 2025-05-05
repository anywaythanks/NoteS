package com.notes.events;

import com.notes.models.entity.SagaEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEvent {
   private UUID sagaId;
   private Long noteId;
   private Long oldCommitId;
   private Long newCommitId;
}
