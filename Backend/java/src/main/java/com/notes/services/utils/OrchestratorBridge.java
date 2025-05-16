package com.notes.services.utils;

import com.notes.events.ScheduleCreateEvent;
import com.notes.events.ScheduleDeleteEvent;
import com.notes.events.ScheduleEditEvent;
import com.notes.mappers.events.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Service
@RequiredArgsConstructor
@Profile("!kafka")
public class OrchestratorBridge {
   private final ApplicationEventPublisher applicationEventPublisher;
   private final EventMapper eventMapper;

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncCreateNote(ScheduleCreateEvent event) {
      applicationEventPublisher.publishEvent(eventMapper.ofCreate(event, this::nop));
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncEditNote(ScheduleEditEvent event) {
      applicationEventPublisher.publishEvent(eventMapper.ofEdit(event, this::nop));
   }

   @TransactionalEventListener(phase = AFTER_COMMIT)
   public void syncDeleteNote(ScheduleDeleteEvent event) {
      applicationEventPublisher.publishEvent(eventMapper.ofDelete(event, this::nop));
   }

   private void nop() {

   }
}