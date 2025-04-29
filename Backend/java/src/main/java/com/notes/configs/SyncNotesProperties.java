package com.notes.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "sync-notes")
public class SyncNotesProperties {
   private Strategy strategy = Strategy.HYBRID_TASK;

   public enum Strategy {
      /**
       * На каждую реиндексацию будет одна таска.
       */
      SINGLE_TASK,
      /**
       * Все реиндексации будут обрабатываться в одной глобальной таске шедулере пакетом.
       */
      PACKET_TASK,
      /**
       * Смешение обоих подходов.
       */
      HYBRID_TASK
   }
}
