package com.notes.services.utils;

import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

/**
 * Utility service for note-related business logic operations.
 */
@Service
@RequiredArgsConstructor
public class NoteUtils {
   private final Clock clock;

   /**
    * Determines if a note is eligible for editing based on creation time.
    *
    * @param note The note to check
    * @return true if the note was created within the last 24 hours, false otherwise
    */
   public boolean isEdit(NotePartialDomainDto note) {
      var nextDay = Instant.now(clock).plus(Duration.ofDays(1));
      return note.createdOn().compareTo(nextDay) <= 0;
   }

   /**
    * Checks if the given note type represents a comment.
    *
    * @param type The note type to check
    * @return true if the type is COMMENT or COMMENT_REDACTED, false otherwise
    */
   public boolean isComment(NoteTypeDomainDto type) {
      return switch(type) {
         case COMMENT, COMMENT_REDACTED -> true;
         default -> false;
      };
   }
}
