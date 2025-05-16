package com.notes.services.utils;

import com.notes.configs.SyncEntriesProperties;
import com.notes.events.ScheduleCreateEvent;
import com.notes.events.ScheduleDeleteEvent;
import com.notes.events.ScheduleEditEvent;
import com.notes.repository.EntryRepositorySchedule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Profile("elastic")
public class OrchestratorScheduler {
   private final SimpleAsyncTaskScheduler taskScheduler;
   private final SyncEntriesProperties syncEntriesProperties;
   private final EntryRepositorySchedule entryRepositorySchedule;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final GeneratorUtils generatorUtils;
   private final TransactionTemplate transactionTemplate;

   @PostConstruct
   public void init() {
      if(syncEntriesProperties.isSchedule()) {
         taskScheduler.scheduleWithFixedDelay(this::createSync, Duration.ofSeconds(5));
         taskScheduler.scheduleWithFixedDelay(this::editSync, Duration.ofSeconds(5));
         taskScheduler.scheduleWithFixedDelay(this::delSync, Duration.ofSeconds(5));
      }
   }

   public void createSync() {
      var l = entryRepositorySchedule.findScheduleCreate();
      for(var entry : l) {
         transactionTemplate.executeWithoutResult(_ ->
                 applicationEventPublisher.publishEvent(new ScheduleCreateEvent(
                         generatorUtils.generateUUID(),
                         entry.getId(),
                         entry.getCommitTo(),
                         entry.getCommitTo()))
         );
      }
   }

   public void editSync() {
      var l = entryRepositorySchedule.findScheduleModify();
      for(var entry : l) {
         transactionTemplate.executeWithoutResult(_ ->
                 applicationEventPublisher.publishEvent(new ScheduleEditEvent(
                         generatorUtils.generateUUID(),
                         entry.getId(),
                         entry.getCommitTo(),
                         entry.getCommitTo()))
         );
      }
   }

   public void delSync() {
      var l = entryRepositorySchedule.findScheduleDelete();
      for(var entry : l) {
         transactionTemplate.executeWithoutResult(_ ->
                 applicationEventPublisher.publishEvent(new ScheduleDeleteEvent(
                         generatorUtils.generateUUID(),
                         entry.getId(),
                         entry.getCommitTo(),
                         entry.getCommitTo()))
         );
      }
   }
}
