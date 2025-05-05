package com.notes.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.notes.models.entity.Note;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository implementation for {@link Note} entities with custom queries.
 * Provides advanced search capabilities and entity graph loading strategies.
 */
@Transactional
public interface NoteRepositoryQuery extends EntityGraphJpaRepository<Note, Long>, NoteRepositorySearchQuery {
   /**
    * Finds a note by its path with owner entity graph loaded.
    *
    * @param path The unique path identifier (non-null)
    * @return {@link Optional} containing the note or empty if not found
    */
   @Query("from Note n where n.path = :path")
   @EntityGraph(value = "Note.actual.full", type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"mainNote.path"})
   Optional<Note> findByPath(@NonNull @Param("path") String path);

   @Query("from Note n where n.path = :path")
   Optional<Note> findByPathMinimal(@NonNull @Param("path") String path);

   /**
    * Retrieves public comments for a note with pagination.
    *
    * @param noteId   The ID of the parent note (non-null)
    * @param pageable Pagination information
    * @return {@link Page} of comment entities
    */
   @Query("""
           from Note n where n.mainNote.id = :noteId
           and (n.noteType = :#{T(com.notes.models.entity.NoteType).COMMENT}
             or n.noteType = :#{T(com.notes.models.entity.NoteType).COMMENT_REDACTED})
           and n.isPublic = true
           order by n.id""")
   Page<Note> findPublicParents(@NonNull @Param("noteId") Long noteId, Pageable pageable);

   /**
    * Finds public notes of type NOTE belonging to a specific owner.
    *
    * @param ownerId  The ID of the note owner (non-null)
    * @param pageable Pagination configuration
    * @return {@link Page} of note entities ordered by ID
    */
   @Query("""
           from Note n where n.owner.id = :ownerId
           and (n.noteType = :#{T(com.notes.models.entity.NoteType).NOTE})
           order by n.id""")
   @EntityGraph(value = "Note.actual.partial", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findByOwner(@NonNull @Param("ownerId") Long ownerId, Pageable pageable);
}