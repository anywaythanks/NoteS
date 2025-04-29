package com.notes.services.utils;

import com.notes.models.domain.NoteFullDomainDto;
import com.notes.models.domain.NoteMinimalDomainDto;
import com.notes.models.domain.NotePartialDomainDto;
import com.notes.models.domain.NoteSearchDomainDto;
import com.notes.models.domain.NoteTypeDomainDto;
import com.notes.models.entity.Note;
import com.notes.models.entity.State;
import lombok.RequiredArgsConstructor;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static com.notes.models.domain.SyntaxTypeDomainDto.PLAINTEXT;

/**
 * Utility service for note-related business logic operations.
 */
@Service
@RequiredArgsConstructor
public class NoteUtils {
   private final Clock clock;
   private final Parser markdownParser;
   private final TextContentRenderer textRenderer;

   /**
    * Determines if a note is eligible for editing based on creation time.
    *
    * @param note The note to check
    * @return true if the note was created within the last 24 hours, false otherwise
    */
   public boolean isEdit(NoteMinimalDomainDto note) {
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

   public NotePartialDomainDto cleanContent(NotePartialDomainDto note) {
      return switch(note.syntaxType()) {
         case PLAINTEXT -> note;
         case MARKDOWN -> note.withContent(convertMarkdownToText(note.content()))
                 .withSyntaxType(PLAINTEXT);
      };
   }

   //TODO: Уровень репозитория на уровне сервисов..
   public boolean updateState(Note note, State newState) {
      if(!note.getState().isValidTransition(newState)) return false;
      note.setState(newState);
      return true;
   }

   private String convertMarkdownToText(String markdown) {
      var document = markdownParser.parse(markdown);
      return textRenderer.render(document);
   }
}
