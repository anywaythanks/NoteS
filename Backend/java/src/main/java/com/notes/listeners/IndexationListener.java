package com.notes.listeners;

import com.notes.events.SyncCreateEvent;
import com.notes.events.SyncDeleteEvent;
import com.notes.events.SyncEditEvent;
import com.notes.mappers.events.EventMapper;
import com.notes.mappers.repository.EntryRepositoryMapper;
import com.notes.models.entity.NoteContent;
import com.notes.repository.inner.NoteRepositoryElastic;
import com.notes.repository.inner.TransactionProxy;
import com.notes.services.utils.EntryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.notes.models.entity.SagaEvent.ERROR_CREATED;
import static com.notes.models.entity.SagaEvent.ERROR_DELETED;
import static com.notes.models.entity.SagaEvent.ERROR_EDITED;
import static com.notes.models.entity.SagaEvent.SUCCESS_CREATED;
import static com.notes.models.entity.SagaEvent.SUCCESS_DELETED;
import static com.notes.models.entity.SagaEvent.SUCCESS_EDITED;
import static com.notes.models.entity.State.ACTIVE;
import static com.notes.models.entity.State.ACTIVE_MODIFIED;
import static com.notes.models.entity.State.ARCHIVED;

@Component
@RequiredArgsConstructor
@Profile("elastic")
public class IndexationListener {
   private final TransactionProxy proxy;
   private final NoteRepositoryElastic repositoryElastic;
   private final EntryRepositoryMapper entryRepositoryMapper;
   private final ApplicationEventPublisher applicationEventPublisher;
   private final EventMapper eventMapper;
   private final EntryUtils entryUtils;

   @EventListener
   public void syncCreateNote(SyncCreateEvent event) {
      var note = proxy.getNote(event.getNoteId());
      try {
         var clean = entryUtils.cleanContent(entryRepositoryMapper.ofPartial(note));
         repositoryElastic.save(NoteContent
                 .builder()
                 .content(clean.content())
                 .syntaxType(entryRepositoryMapper.of(clean.syntaxType()))
                 .title(clean.title())
                 .uuid(note.getElasticUuid())
                 .owner(note.getOwner().getId())
                 .entryType(note.getEntryType())
                 .build());
         proxy.updateNoteState(event.getNoteId(), ACTIVE);
         applicationEventPublisher.publishEvent(eventMapper.ofSuccess(event, SUCCESS_CREATED));
      } catch(Exception e) {
         applicationEventPublisher.publishEvent(eventMapper.ofFailed(event, ERROR_CREATED));
      }
   }

   @EventListener
   public void syncEditNote(SyncEditEvent event) {
      var note = proxy.getNote(event.getNoteId());
      try {
         var clean = entryUtils.cleanContent(entryRepositoryMapper.ofPartial(note));
         repositoryElastic.save(NoteContent
                 .builder()
                 .content(clean.content())
                 .syntaxType(entryRepositoryMapper.of(clean.syntaxType()))
                 .title(clean.title())
                 .uuid(note.getElasticUuid())
                 .owner(note.getOwner().getId())
                 .build());
         proxy.updateNoteState(event.getNoteId(), ACTIVE_MODIFIED);
         applicationEventPublisher.publishEvent(eventMapper.ofSuccess(event, SUCCESS_EDITED));
      } catch(Exception e) {
         applicationEventPublisher.publishEvent(eventMapper.ofFailed(event, ERROR_EDITED));
      }
   }

   @EventListener
   public void syncDeleteNote(SyncDeleteEvent event) {
      var note = proxy.getNote(event.getNoteId());
      try {
         repositoryElastic.findById(note.getElasticUuid().toString()).ifPresent(repositoryElastic::delete);
         proxy.updateNoteState(event.getNoteId(), ARCHIVED);
         applicationEventPublisher.publishEvent(eventMapper.ofSuccess(event, SUCCESS_DELETED));
      } catch(Exception e) {
         applicationEventPublisher.publishEvent(eventMapper.ofFailed(event, ERROR_DELETED));
      }
   }
}
