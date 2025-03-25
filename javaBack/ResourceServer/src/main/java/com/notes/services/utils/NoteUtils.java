package com.notes.services.utils;

import com.notes.models.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NoteUtils {
   private final Clock clock;

   private boolean isEdit(Note note) {
      var nextDay = Instant.now(clock).plus(Duration.ofDays(1));
      return note.getCreatedOn() != null &&
              note.getCreatedOn().compareTo(nextDay) <= 0;
   }
}
