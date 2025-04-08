package com.notes.repository;

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
public interface NoteRepositoryDb extends JpaRepository<Note, Long> {
   /**
    * Finds a note by its path with owner entity graph loaded.
    *
    * @param path The unique path identifier (non-null)
    * @return {@link Optional} containing the note or empty if not found
    */
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"mainNote.path"})
   Optional<Note> findByPath(@NonNull String path);

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
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findComments(@NonNull @Param("noteId") Long noteId, Pageable pageable);

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
           and n.isPublic = true
           order by n.id""")
   Page<Note> findNotesByOwner(@NonNull @Param("ownerId") Long ownerId, Pageable pageable);


   /**
    * Performs strict tag search requiring all specified tags while excluding filter tags.
    *
    * @param tags       List of required tag IDs
    * @param filterTags List of excluded tag IDs
    * @param ownerId    The ID of the note owner
    * @param pageable   Pagination configuration
    * @return {@link Page} of notes matching all required tags and no filter tags
    */
   @Query("""
           select n from Note n
           inner join NoteTagRef ntr on ntr.note.id = n.id
           where n.owner.id = :ownerId
           and n.noteType = :#{T(com.notes.models.entity.NoteType).NOTE}
           and ntr.tag.id in :tags
           and not exists (
               select 1 from NoteTagRef ntr2
               where ntr2.note.id = n.id
               and ntr2.tag.id in :filterTags
           )
           group by n
           having count(distinct ntr.tag.id) = :tagsSize
           order by n.id""")
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findStrongByTagId(@Param("tags") List<Long> tags,
                                @Param("filterTags") List<Long> filterTags,
                                @Param("ownerId") Long ownerId,
                                Pageable pageable);

   /**
    * Performs relaxed tag search including any of specified tags while excluding filter tags.
    *
    * @param tags       List of desired tag IDs
    * @param filterTags List of excluded tag IDs
    * @param ownerId    The ID of the note owner
    * @param pageable   Pagination configuration
    * @return {@link Page} of notes containing any of the specified tags and no filter tags
    */
   @Query("""
           from Note n
               left join NoteTagRef ntr on ntr.note.id = n.id
           where n.owner.id = :ownerId
           and n.noteType = :#{T(com.notes.models.entity.NoteType).NOTE}
           and ntr.tag.id in :tags
           and not ntr.tag.id in :filterTags
           order by n.id""")
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findWeakByTagId(@Param("tags") List<Long> tags,
                              @Param("filterTags") List<Long> filterTags,
                              @Param("ownerId") Long ownerId,
                              Pageable pageable);

   /**
    * Retrieves notes by their Elasticsearch UUIDs.
    *
    * @param uuids List of Elasticsearch document IDs
    * @return {@link Iterable} of notes in ID order
    */
   @Query("""
           from Note n
           where n.elasticUuid in :uuids
           order by n.id""")
   Iterable<Note> findByElasticUuids(@Param("uuids") List<String> uuids);
}