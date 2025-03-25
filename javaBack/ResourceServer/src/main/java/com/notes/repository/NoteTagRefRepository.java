package com.notes.repository;

import com.notes.models.NoteTagRef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface NoteTagRefRepository extends JpaRepository<NoteTagRef, Long>, NoteRepositoryHibernate {
   @Query("from NoteTagRef ntr where ntr.note.id = :noteId")
   @EntityGraph(value = "NoteTagRef.tag", type = EntityGraph.EntityGraphType.LOAD)
   List<NoteTagRef> findByNoteId(@Param("noteId") Integer noteId);

   @Query("from NoteTagRef ntr where ntr.tag.id = :tagId")
   @EntityGraph(value = "NoteTagRef.note", type = EntityGraph.EntityGraphType.LOAD)
   List<NoteTagRef> findByTagId(@Param("tagId") Integer tagId);

   @Query("""
           from NoteTagRef ntr where ntr.tag.id in (:tags)
           and not ntr.tag.id in (:filterTags)""")
   @EntityGraph(value = "NoteTagRef.note", type = EntityGraph.EntityGraphType.LOAD)
   Page<NoteTagRef> findByTagId(@Param("tags") List<Integer> tags,
                                @Param("filterTags") List<Integer> filterTags,
                                @Param("op") boolean op,
                                @Param("ownerId") Integer ownerId,
                                Pageable pageable);

}