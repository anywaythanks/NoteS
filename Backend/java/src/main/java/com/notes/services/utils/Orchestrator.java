package com.notes.services.utils;

import com.notes.events.FailedSync;
import com.notes.events.ScheduleCreateEvent;
import com.notes.events.ScheduleEvent;
import com.notes.events.SuccessSync;
import com.notes.mappers.events.EventMapper;
import com.notes.models.entity.Commit;
import com.notes.models.entity.Note;
import com.notes.models.entity.SagaLog;
import com.notes.repository.SagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Service
@RequiredArgsConstructor
public class Orchestrator {
   private final KafkaTemplate<String, ScheduleEvent> kafkaTemplate;
   private final GeneratorUtils generatorUtils;
   private final SagaRepository sagaRepository;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final EventMapper eventMapper;

   @KafkaListener(topics = "sync-create")
   @RetryableTopic(
           attempts = "3",
           backoff = @Backoff(delay = 2000, multiplier = 2),
           dltTopicSuffix = "-dlt"
   )
   public void handleCreateSync(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofCreate(event, ack::acknowledge));
   }

   @KafkaListener(topics = "sync-edit")
   @RetryableTopic(
           attempts = "3",
           backoff = @Backoff(delay = 2000, multiplier = 2),
           dltTopicSuffix = "-dlt"
   )
   public void handleEditSync(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofEdit(event, ack::acknowledge));
   }

   @KafkaListener(topics = "sync-delete")
   @RetryableTopic(
           attempts = "3",
           backoff = @Backoff(delay = 2000, multiplier = 2),
           dltTopicSuffix = "-dlt"
   )
   public void handleDeleteSync(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofDelete(event, ack::acknowledge));
   }

   @KafkaListener(topics = {"sync-create-dlt", "sync-edit-dlt", "sync-delete-dlt"})
   public void handleFail(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofCompensate(event, ack::acknowledge));
   }

   @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
   public void handleSaga(ScheduleEvent event) {
      var saga = SagaLog.builder()
              .event(event.getEventType())
              .sagaUuid(event.getSagaId())
              .note(Note.builder().id(event.getNoteId()).build())
              .commit(Commit.builder().id(event.getNewCommitId()).build())
              .payload(Map.of())
              .build();
      sagaRepository.save(saga);
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncCreateNote(ScheduleCreateEvent event) {
      kafkaTemplate.send("sync-create", event.getSagaId().toString(), event);
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncEditNote(ScheduleCreateEvent event) {
      kafkaTemplate.send("sync-edit", event.getSagaId().toString(), event);
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncDeleteNote(ScheduleCreateEvent event) {
      kafkaTemplate.send("sync-delete", event.getSagaId().toString(), event);
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void success(SuccessSync event) {
      event.getAcknowledgment().confirm();
   }


   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void failed(FailedSync event) {
      //ignore ???
   }
}