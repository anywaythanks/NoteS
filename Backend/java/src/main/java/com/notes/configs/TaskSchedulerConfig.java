package com.notes.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;

@Configuration
@EnableScheduling
public class TaskSchedulerConfig {
   @Bean
   public SimpleAsyncTaskScheduler taskScheduler() {
      return new SimpleAsyncTaskScheduler();
   }
}
