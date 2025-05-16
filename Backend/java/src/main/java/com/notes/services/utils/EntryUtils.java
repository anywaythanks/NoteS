package com.notes.services.utils;

import com.notes.models.domain.EntryMinimalDomainDto;
import com.notes.models.domain.EntryPartialDomainDto;
import com.notes.models.domain.EntryTypeDomainDto;
import com.notes.models.entity.Entry;
import com.notes.models.entity.Entry_;
import com.notes.models.entity.State;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
public class EntryUtils {
   private final Clock clock;
   private final Parser markdownParser;
   private final TextContentRenderer textRenderer;

   /**
    * Determines if a note is eligible for editing based on creation time.
    *
    * @param note The note to check
    * @return true if the note was created within the last 24 hours, false otherwise
    */
   public boolean isEdit(EntryMinimalDomainDto note) {
      var nextDay = Instant.now(clock).plus(Duration.ofDays(1));
      return note.createdOn().compareTo(nextDay) <= 0;
   }

   /**
    * Checks if the given note type represents a comment.
    *
    * @param type The note type to check
    * @return true if the type is COMMENT or COMMENT_REDACTED, false otherwise
    */
   public boolean isComment(EntryTypeDomainDto type) {
      return type == EntryTypeDomainDto.COMMENT;
   }

   public EntryPartialDomainDto cleanContent(EntryPartialDomainDto note) {
      return switch(note.syntaxType()) {
         case PLAINTEXT -> note;
         case MARKDOWN -> note.withContent(convertMarkdownToText(note.content()))
                 .withSyntaxType(PLAINTEXT);
      };
   }

   //TODO: Уровень репозитория на уровне сервисов..
   public boolean updateState(Entry entry, State newState) {
      if(!entry.getState().isValidTransition(newState)) return false;
      entry.setState(newState);
      return true;
   }

   private String convertMarkdownToText(String markdown) {
      var document = markdownParser.parse(markdown);
      return textRenderer.render(document);
   }

   public Predicate softDelPredicate(Root<Entry> root, CriteriaBuilder cb) {
      return softDelPredicate(root.get(Entry_.state), cb);
   }

   public Predicate softDelPredicate(Path<State> path, CriteriaBuilder cb) {
      return cb.not(cb.or(cb.equal(path, State.PENDING_ARCHIVE), cb.equal(path, State.ARCHIVED)));
   }
}
