package com.notes.repository.inner;

import com.notes.configs.SyncEntriesProperties;
import com.notes.events.CompensateEvent;
import com.notes.events.ScheduleCreateEvent;
import com.notes.events.ScheduleDeleteEvent;
import com.notes.events.ScheduleEditEvent;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.models.entity.Account;
import com.notes.models.entity.Commit;
import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryCreateDto;
import com.notes.models.entity.EntryEditDto;
import com.notes.repository.EntryRepositoryCommand;
import com.notes.services.utils.EntryUtils;
import com.notes.services.utils.GeneratorUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.notes.configs.SyncEntriesProperties.Strategy.SINGLE_TASK;
import static com.notes.models.entity.State.FAILED;
import static com.notes.models.entity.State.PENDING_ARCHIVE;
import static com.notes.models.entity.State.PENDING_CREATE;
import static com.notes.models.entity.State.PENDING_MODIFY;

@Repository
@RequiredArgsConstructor
public class EntryRepositoryCommandImpl implements EntryRepositoryCommand {
   @PersistenceContext
   private final EntityManager em;
   private final SyncEntriesProperties properties;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final GeneratorUtils generatorUtils;
   private final EntryUtils entryUtils;

   @Override
   @Transactional
   public void create(EntryCreateDto noteDto) {
      var main = noteDto.mainId() != null ? Entry.builder().id(noteDto.mainId()).build() : null;
      var note = Entry.builder()
              .entryType(noteDto.entryType())
              .elasticUuid(noteDto.elasticUuid())
              .path(noteDto.path())
              .owner(Account
                      .builder()
                      .id(noteDto.ownerId())
                      .build())
              .mainEntry(main)
              .state(PENDING_CREATE)
              .isPublic(noteDto.isPublic())
              .build();
      em.persist(note);
      var commit = Commit.builder()
              .note_id(note.getId())
              .syntaxType(noteDto.syntaxType())
              .title(noteDto.title())
              .content(noteDto.content())
              .description(noteDto.description())
              .build();
      note.setActual(commit);
      em.persist(commit);
      if(properties.getStrategy() == SINGLE_TASK) {
         applicationEventPublisher.publishEvent(new ScheduleCreateEvent(
                 generatorUtils.generateUUID(),
                 note.getId(),
                 commit.getId(),
                 commit.getId()));
      }
   }

   @Override
   public void edit(Long noteId, EntryEditDto noteDto) {
      var note = getNote(noteId);
      var oldCommit = note.getCommitTo();
      var commit = Commit.builder()
              .note_id(noteId)
              .syntaxType(noteDto.syntaxType())
              .title(noteDto.title())
              .content(noteDto.content())
              .description(noteDto.description())
              .build();
      em.persist(commit);
      note.setActual(commit);
      entryUtils.updateState(note, PENDING_MODIFY);
      em.persist(note);
      if(properties.getStrategy() == SINGLE_TASK) {
         applicationEventPublisher.publishEvent(new ScheduleEditEvent(
                 generatorUtils.generateUUID(),
                 note.getId(),
                 oldCommit,
                 commit.getId()));
      }

   }

   @Override
   public void delete(Long noteId) {
      var note = getNote(noteId);
      entryUtils.updateState(note, PENDING_ARCHIVE);
      if(properties.getStrategy() == SINGLE_TASK) {
         applicationEventPublisher.publishEvent(new ScheduleDeleteEvent(
                 generatorUtils.generateUUID(),
                 note.getId(),
                 note.getCommitTo(),
                 note.getCommitTo()));
      }
   }

   @Override
   public void publish(Long noteId, boolean isPublic) {
      var note = em.find(Entry.class, noteId);
      if(note == null) throw new NoteNotFoundException();
      note.setIsPublic(isPublic);
      em.persist(note);
   }

   @TransactionalEventListener
   public void compensate(CompensateEvent event) {
      var note = getNote(event.getNoteId());
      entryUtils.updateState(note, FAILED);
   }

   private Entry getNote(Long id) {
      var note = em.find(Entry.class, id);
      if(note == null) throw new NoteNotFoundException();
      return note;
   }
}
