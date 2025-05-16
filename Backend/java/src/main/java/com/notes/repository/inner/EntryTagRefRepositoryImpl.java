package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryTagRef;
import com.notes.models.entity.EntryTagRef_;
import com.notes.models.entity.Entry_;
import com.notes.models.entity.Tag;
import com.notes.models.entity.Tag_;
import com.notes.services.utils.EntryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Transactional
@Repository
@RequiredArgsConstructor
public class EntryTagRefRepositoryImpl implements EntryTagRefCustomRepository {
   private final EntryTagRefDb db;
   private final EntryUtils entryUtils;

   @Override
   public List<Tag> findByNoteId(Long noteId) {
      return db.findAll(
                      where(hasNoteId(noteId)).and(activeEntry()),
                      Sort.unsorted(),
                      new NamedEntityGraph(EntityGraphType.LOAD, EntryTagRef_.GRAPH_ENTRY_TAG_REF_TAG)
              ).stream()
              .map(EntryTagRef::getTag)
              .collect(Collectors.toList());
   }

   @Override
   public List<Entry> findByTagId(Long tagId) {
      return db.findAll(
                      where(hasTagId(tagId)).and(activeEntry()),
                      Sort.by(Sort.Direction.ASC, EntryTagRef_.ENTRY + "." + Entry_.ID),
                      new NamedEntityGraph(EntityGraphType.LOAD, EntryTagRef_.GRAPH_ENTRY_TAG_REF_ENTRY)
              ).stream()
              .map(EntryTagRef::getEntry)
              .collect(Collectors.toList());
   }

   @Override
   public List<EntryTagRef> findByNoteIds(List<Long> notesIds) {
      return db.findAll(
              where(hasNoteIds(notesIds)).and(activeEntry()),
              Sort.unsorted(),
              new NamedEntityGraph(EntityGraphType.LOAD, EntryTagRef_.GRAPH_ENTRY_TAG_REF_TAG)
      );
   }

   private Specification<EntryTagRef> hasNoteId(Long noteId) {
      return (root, query, cb) ->
              cb.equal(root.get(EntryTagRef_.entry).get(Entry_.ID), noteId);
   }

   private Specification<EntryTagRef> hasTagId(Long tagId) {
      return (root, query, cb) ->
              cb.equal(root.get(EntryTagRef_.tag).get(Tag_.ID), tagId);
   }

   private Specification<EntryTagRef> hasNoteIds(List<Long> noteIds) {
      return (root, query, cb) ->
              root.get(EntryTagRef_.entry).get(Entry_.ID).in(noteIds);
   }

   private Specification<EntryTagRef> activeEntry() {
      return (root, _, cb) ->
              entryUtils.softDelPredicate(root.get(EntryTagRef_.entry).get(Entry_.state), cb);
   }
}
