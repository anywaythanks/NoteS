package com.notes.services.utils;

import com.notes.events.FailedSync;
import com.notes.events.ScheduleCreateEvent;
import com.notes.events.ScheduleDeleteEvent;
import com.notes.events.ScheduleEditEvent;
import com.notes.events.ScheduleEvent;
import com.notes.events.SuccessSync;
import com.notes.mappers.events.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Service
@RequiredArgsConstructor
@Profile("kafka")
public class OrchestratorKafka {
   private final KafkaTemplate<String, ScheduleEvent> kafkaTemplate;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final EventMapper eventMapper;

   @KafkaListener(topics = "sync-create", groupId = "notes")
   @RetryableTopic(
           attempts = "3",
           backoff = @Backoff(delay = 2000, multiplier = 2),
           dltTopicSuffix = "-dlt"
   )
   public void handleCreateSync(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofCreate(event, ack::acknowledge));
   }

   @KafkaListener(topics = "sync-edit", groupId = "notes")
   @RetryableTopic(
           attempts = "3",
           backoff = @Backoff(delay = 2000, multiplier = 2),
           dltTopicSuffix = "-dlt"
   )
   public void handleEditSync(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofEdit(event, ack::acknowledge));
   }

   @KafkaListener(topics = "sync-delete", groupId = "notes")
   @RetryableTopic(
           attempts = "3",
           backoff = @Backoff(delay = 2000, multiplier = 2),
           dltTopicSuffix = "-dlt"
   )
   public void handleDeleteSync(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofDelete(event, ack::acknowledge));
   }

   @KafkaListener(topics = {"sync-create-dlt", "sync-edit-dlt", "sync-delete-dlt"}, groupId = "notes")
   public void handleFail(ScheduleEvent event, Acknowledgment ack) {
      applicationEventPublisher.publishEvent(eventMapper.ofCompensate(event, ack::acknowledge));
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncCreateNote(ScheduleCreateEvent event) {
      kafkaTemplate.send("sync-create", event.getSagaId().toString(), event);
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncEditNote(ScheduleEditEvent event) {
      kafkaTemplate.send("sync-edit", event.getSagaId().toString(), event);
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncDeleteNote(ScheduleDeleteEvent event) {
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