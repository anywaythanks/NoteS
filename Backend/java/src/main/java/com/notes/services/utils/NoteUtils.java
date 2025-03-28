package com.notes.services.utils;

import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NoteUtils {
   private final Clock clock;

   public boolean isEdit(NotePartialDomainDto note) {
      var nextDay = Instant.now(clock).plus(Duration.ofDays(1));
      return note.createdOn().compareTo(nextDay) <= 0;
   }

   public boolean isComment(NoteTypeDomainDto type) {
      return switch (type) {
         case COMMENT, COMMENT_REDACTED -> true;
         default -> false;
      };
   }
}
