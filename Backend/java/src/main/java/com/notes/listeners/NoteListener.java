package com.notes.listeners;

import com.notes.models.entity.Entry;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class NoteListener {
   private final Clock clock;

   @PrePersist
   public void setCreatedOn(Entry entry) {
      entry.setCreatedOn(Instant.now(clock));
   }
}
