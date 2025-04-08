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

/**
 * JPA repository for managing {@link NoteTagRef} entities representing relationships between notes and tags.
 * Provides methods for querying tag-note associations with entity graph loading.
 */
@Transactional
public interface NoteTagRefRepository extends JpaRepository<NoteTagRef, NoteTagRef.NoteTagId> {

   /**
    * Retrieves all tags associated with a specific note, loading the tag entities.
    *
    * @param noteId The ID of the note to find tags for
    * @return List of {@link Tag} entities associated with the note
    */
   @Query("select ntr.tag from NoteTagRef ntr where ntr.note.id = :noteId")
   @EntityGraph(value = "NoteTagRef.tag", type = EntityGraph.EntityGraphType.LOAD)
   List<Tag> findByNoteId(@Param("noteId") Long noteId);

   /**
    * Retrieves all notes associated with a specific tag, loading the note entities.
    *
    * @param tagId The ID of the tag to find notes for
    * @return List of {@link Note} entities associated with the tag
    */
   @Query("select ntr.note from NoteTagRef ntr where ntr.tag.id = :tagId")
   @EntityGraph(value = "NoteTagRef.note", type = EntityGraph.EntityGraphType.LOAD)
   List<Note> findByTagId(@Param("tagId") Long tagId);

   /**
    * Retrieves all note-tag relationships for a list of note IDs, loading the tag entities.
    *
    * @param notesIds List of note IDs to find relationships for
    * @return List of {@link NoteTagRef} entities with loaded tags
    */
   @Query("from NoteTagRef ntr where ntr.note.id in :notesIds")
   @EntityGraph(value = "NoteTagRef.tag", type = EntityGraph.EntityGraphType.LOAD)
   List<NoteTagRef> findByNoteIds(@Param("notesIds") List<Long> notesIds);
}