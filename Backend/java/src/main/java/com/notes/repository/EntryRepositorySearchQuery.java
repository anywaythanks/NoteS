package com.notes.repository;

import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryScored;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EntryRepositorySearchQuery {
   Page<EntryScored> semanticSearch(String query,
                                    Long ownerId,
                                    Pageable pageable);

   Page<EntryScored> searchByTitle(String title,
                                   Long ownerId,
                                   Pageable pageable);

   Page<Entry> findByTags(List<Long> tags,
                          List<Long> filterTags,
                          Long ownerId,
                          boolean isStrong,
                          Pageable pageable);

   /**
    * Finds a entry by its path with owner entity graph loaded.
    *
    * @param path The unique path identifier (non-null)
    * @return {@link Optional} containing the entry or empty if not found
    */
   Optional<Entry> findByPath(String path);

   Optional<Entry> findByPathMinimal(String path);

   /**
    * Retrieves public comments for a entry with pagination.
    *
    * @param noteId   The ID of the parent entry (non-null)
    * @param pageable Pagination information
    * @return {@link Page} of comment entities
    */
   Page<Entry> findPublicParents(Long noteId, Pageable pageable);

   /**
    * Finds public notes of type NOTE belonging to a specific owner.
    *
    * @param ownerId  The ID of the entry owner (non-null)
    * @param pageable Pagination configuration
    * @return {@link Page} of entry entities ordered by ID
    */
   Page<Entry> findByOwner(Long ownerId, Pageable pageable);
}