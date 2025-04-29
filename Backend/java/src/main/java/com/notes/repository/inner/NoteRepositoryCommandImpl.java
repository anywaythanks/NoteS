package com.notes.repository.inner;

import com.notes.configs.SyncNotesProperties;
import com.notes.events.CompensateEvent;
import com.notes.events.ScheduleCreateEvent;
import com.notes.events.ScheduleDeleteEvent;
import com.notes.events.ScheduleEditEvent;
import com.notes.events.SyncCreateEvent;
import com.notes.events.SyncDeleteEvent;
import com.notes.events.SyncEditEvent;
import com.notes.exceptions.NoteNotFoundException;
import com.notes.mappers.events.EventMapper;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.entity.Account;
import com.notes.models.entity.Commit;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteCreateDto;
import com.notes.models.entity.NoteEditDto;
import com.notes.repository.NoteRepositoryCommand;
import com.notes.services.utils.GeneratorUtils;
import com.notes.services.utils.NoteUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Clock;
import java.time.Instant;

import static com.notes.configs.SyncNotesProperties.Strategy.SINGLE_TASK;
import static com.notes.models.entity.State.ACTIVE;
import static com.notes.models.entity.State.ARCHIVED;
import static com.notes.models.entity.State.FAILED;
import static com.notes.models.entity.State.PENDING;
import static com.notes.models.entity.State.PENDING_ARCHIVE;

@Repository
@RequiredArgsConstructor
public class NoteRepositoryCommandImpl implements NoteRepositoryCommand {
   @PersistenceContext
   private final EntityManager em;
   private final TransactionProxy proxy;
   private final SyncNotesProperties properties;
   private final NoteRepositoryElastic repositoryElastic;
   private final NoteRepositoryMapper noteRepositoryMapper;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final GeneratorUtils generatorUtils;
   private final Clock clock;
   private final EventMapper eventMapper;
   private final NoteUtils noteUtils;

   @Override
   @Transactional
   public void create(NoteCreateDto noteDto) {
      var main = noteDto.mainId() != null ? Note.builder().id(noteDto.mainId()).build() : null;
      var note = Note.builder()
              .noteType(noteDto.noteType())
              .elasticUuid(noteDto.elasticUuid())
              .path(noteDto.path())
              .owner(Account
                      .builder()
                      .id(noteDto.ownerId())
                      .build())
              .mainNote(main)
              .state(PENDING)
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
                 commit.getId(),
                 Instant.now(clock)));
      }
   }

   @TransactionalEventListener
   public void syncCreateNote(SyncCreateEvent event) {
      var note = proxy.getNote(event.getNoteId());
      try {
         var clean = noteUtils.cleanContent(noteRepositoryMapper.ofPartial(note));
         repositoryElastic.save(NoteContent
                 .builder()
                 .content(clean.content())
                 .syntaxType(noteRepositoryMapper.of(clean.syntaxType()))
                 .title(clean.title())
                 .uuid(note.getElasticUuid())
                 .owner(note.getOwner().getId())
                 .build());
         proxy.updateNoteState(event.getNoteId(), ACTIVE);
         applicationEventPublisher.publishEvent(eventMapper.ofSuccess(event));
      } catch(Exception e) {
         applicationEventPublisher.publishEvent(eventMapper.ofFailed(event));
      }
   }

   @Override
   public void edit(Long noteId, NoteEditDto noteDto) {
      var note = em.find(Note.class, noteId);
      if(note == null) throw new NoteNotFoundException();
      var commit = Commit.builder()
              .note_id(noteId)
              .syntaxType(noteDto.syntaxType())
              .title(noteDto.title())
              .content(noteDto.content())
              .description(noteDto.description())
              .build();
      em.persist(commit);
      note.setActual(commit);
      noteUtils.updateState(note, PENDING);
      em.persist(note);
      if(properties.getStrategy() == SINGLE_TASK) {
         applicationEventPublisher.publishEvent(new ScheduleEditEvent(
                 generatorUtils.generateUUID(),
                 note.getId(),
                 commit.getId(),
                 commit.getId(),
                 Instant.now(clock)));
      }

   }

   @TransactionalEventListener
   public void syncEditNote(SyncEditEvent event) {
      var note = proxy.getNote(event.getNoteId());
      try {
         var clean = noteUtils.cleanContent(noteRepositoryMapper.ofPartial(note));
         repositoryElastic.save(NoteContent
                 .builder()
                 .content(clean.content())
                 .syntaxType(noteRepositoryMapper.of(clean.syntaxType()))
                 .title(clean.title())
                 .uuid(note.getElasticUuid())
                 .owner(note.getOwner().getId())
                 .build());
         proxy.updateNoteState(event.getNoteId(), ACTIVE);
         applicationEventPublisher.publishEvent(eventMapper.ofSuccess(event));
      } catch(Exception e) {
         applicationEventPublisher.publishEvent(eventMapper.ofFailed(event));
      }
   }

   @Override
   public void delete(Long noteId) {
      var note = em.find(Note.class, noteId);
      if(note == null) throw new NoteNotFoundException();
      noteUtils.updateState(note, PENDING_ARCHIVE);
      if(properties.getStrategy() == SINGLE_TASK) {
         applicationEventPublisher.publishEvent(new ScheduleDeleteEvent(
                 generatorUtils.generateUUID(),
                 note.getId(),
                 note.getCommitTo(),
                 note.getCommitTo(),
                 Instant.now(clock)));
      }
   }

   @TransactionalEventListener
   public void syncDeleteNote(SyncDeleteEvent event) {
      var note = proxy.getNote(event.getNoteId());
      try {
         repositoryElastic.deleteById(note.getElasticUuid().toString());
         proxy.updateNoteState(event.getNoteId(), ARCHIVED);
         applicationEventPublisher.publishEvent(eventMapper.ofSuccess(event));
      } catch(Exception e) {
         applicationEventPublisher.publishEvent(eventMapper.ofFailed(event));
      }
   }

   @TransactionalEventListener
   public void compensate(CompensateEvent event) {
      var note = em.find(Note.class, event.getNoteId());
      if(note == null) throw new NoteNotFoundException();
      noteUtils.updateState(note, FAILED);
   }

   @Override
   public void publish(Long noteId, boolean isPublic) {
      var note = em.find(Note.class, noteId);
      if(note == null) throw new NoteNotFoundException();
      note.setIsPublic(isPublic);
      em.persist(note);
   }
}
