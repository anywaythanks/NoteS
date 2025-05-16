package com.notes.repository.inner;

import com.notes.exceptions.NoteNotFoundException;
import com.notes.models.entity.Entry;
import com.notes.models.entity.Entry_;
import com.notes.models.entity.State;
import com.notes.services.utils.EntryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType.LOAD;

@Component
@RequiredArgsConstructor
public class TransactionProxy {
   @PersistenceContext
   private final EntityManager em;
   private final EntryUtils entryUtils;

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public Entry getNote(Long id) {
      var note = em.find(Entry.class, id, Collections.singletonMap(
              LOAD.key(), em.getEntityGraph(Entry_.GRAPH_ENTRY_ACTUAL_FULL)
      ));
      if(note == null) throw new NoteNotFoundException();
      return note;
   }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public boolean updateNoteState(Long noteId, State state) {
      var note = em.find(Entry.class, noteId);
      return entryUtils.updateState(note, state);
   }
}
