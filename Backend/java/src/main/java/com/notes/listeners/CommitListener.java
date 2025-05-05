package com.notes.listeners;

import com.notes.models.entity.Commit;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CommitListener {
   private final Clock clock;

   @PrePersist
   public void setCreatedOn(Commit commit) {
      commit.setCreatedOn(Instant.now(clock));
   }
}
