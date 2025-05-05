package com.notes.listeners;

import com.notes.models.entity.Commit;
import com.notes.models.entity.SagaLog;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SagaListener {
   private final Clock clock;

   @PrePersist
   public void setCreatedOn(SagaLog sagaLog) {
      sagaLog.setCreatedOn(Instant.now(clock));
   }
}
