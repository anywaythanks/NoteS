package com.notes.repository.inner;

import com.notes.exceptions.NoteNotFoundException;
import com.notes.models.entity.Note;
import com.notes.models.entity.State;
import com.notes.services.utils.NoteUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@RequiredArgsConstructor
class TransactionProxy {
   @PersistenceContext
   private final EntityManager em;
   private final NoteUtils noteUtils;

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public Note getNote(Long id) {
      var note = em.find(Note.class, id, Collections.singletonMap(
              "javax.persistence.loadgraph",
              em.getEntityGraph("Note.actual.full")
      ));
      if(note == null) throw new NoteNotFoundException();
      return note;
   }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public boolean updateNoteState(Long noteId, State state) {
      var note = em.find(Note.class, noteId);
      return noteUtils.updateState(note, state);
   }
}
