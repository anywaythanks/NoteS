package com.notes.repository;

import com.notes.models.entity.Note;
import com.notes.models.entity.NoteTagRef;
import com.notes.models.entity.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface NoteTagRefRepository extends JpaRepository<NoteTagRef, NoteTagRef.NoteTagId> {
   @Query("select ntr.tag from NoteTagRef ntr where ntr.note.id = :noteId")
   @EntityGraph(value = "NoteTagRef.tag", type = EntityGraph.EntityGraphType.LOAD)
   List<Tag> findByNoteId(@Param("noteId") Long noteId);

   @Query("select ntr.note from NoteTagRef ntr where ntr.tag.id = :tagId")
   @EntityGraph(value = "NoteTagRef.note", type = EntityGraph.EntityGraphType.LOAD)
   List<Note> findByTagId(@Param("tagId") Long tagId);

   @Query("from NoteTagRef ntr where ntr.note.id in :notesIds")
   @EntityGraph(value = "NoteTagRef.tag", type = EntityGraph.EntityGraphType.LOAD)
   List<NoteTagRef> findByNoteIds(@Param("notesIds") List<Long> notesIds);
}