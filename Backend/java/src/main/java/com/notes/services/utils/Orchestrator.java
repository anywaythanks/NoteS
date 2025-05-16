package com.notes.services.utils;

import com.notes.events.ScheduleEvent;
import com.notes.models.entity.Commit;
import com.notes.models.entity.Entry;
import com.notes.models.entity.SagaLog;
import com.notes.repository.SagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Profile("elastic")
public class Orchestrator {
   private final SagaRepository sagaRepository;

   @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
   public void handleSaga(ScheduleEvent event) {
      var saga = SagaLog.builder()
              .event(event.getEventType())
              .sagaUuid(event.getSagaId())
              .entry(Entry.builder().id(event.getNoteId()).build())
              .commit(Commit.builder().id(event.getNewCommitId()).build())
              .payload(Map.of())
              .build();
      sagaRepository.save(saga);
   }
}