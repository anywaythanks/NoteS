package com.notes.repository.inner;

import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryTagRef;
import com.notes.models.entity.EntryTagRef_;
import com.notes.models.entity.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EntryTagRefCustomRepository {

   /**
    * Retrieves all tags associated with a specific note, loading the tag entities.
    *
    * @param noteId The ID of the note to find tags for
    * @return List of {@link Tag} entities associated with the note
    */
   @Query("select etr.tag from EntryTagRef etr where etr.entry.id = :noteId")
   @EntityGraph(value = EntryTagRef_.GRAPH_ENTRY_TAG_REF_TAG, type = EntityGraph.EntityGraphType.LOAD)
   List<Tag> findByNoteId(@Param("noteId") Long noteId);

   /**
    * Retrieves all notes associated with a specific tag, loading the note entities.
    *
    * @param tagId The ID of the tag to find notes for
    * @return List of {@link Entry} entities associated with the tag
    */
   @Query("select etr.entry from EntryTagRef etr where etr.tag.id = :tagId")
   @EntityGraph(value = EntryTagRef_.GRAPH_ENTRY_TAG_REF_ENTRY, type = EntityGraph.EntityGraphType.LOAD)
   List<Entry> findByTagId(@Param("tagId") Long tagId);

   /**
    * Retrieves all note-tag relationships for a list of note IDs, loading the tag entities.
    *
    * @param notesIds List of note IDs to find relationships for
    * @return List of {@link EntryTagRef} entities with loaded tags
    */
   @Query("from EntryTagRef etr where etr.entry.id in :notesIds")
   @EntityGraph(value = EntryTagRef_.GRAPH_ENTRY_TAG_REF_TAG, type = EntityGraph.EntityGraphType.LOAD)
   List<EntryTagRef> findByNoteIds(@Param("notesIds") List<Long> notesIds);
}