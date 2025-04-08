package com.notes.repository;

import com.notes.models.entity.Note;
import com.notes.models.entity.NoteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for managing notes with custom operations.
 * Handles complex note operations including search and comments management.
 *
 * <p>Encapsulates distributed transaction logic across different data sources.
 */
@Repository
@Transactional
public interface NoteRepository {
   /**
    * Deletes a note entity.
    *
    * @param note The note to delete
    * @return true if deletion was successful, false otherwise
    */
   boolean delete(Note note);

   /**
    * Saves a note DTO and returns the persisted entity.
    *
    * @param noteDto The note data transfer object to save
    * @return The saved note DTO
    */
   NoteDto save(NoteDto noteDto);

   /**
    * Searches notes by title with pagination.
    *
    * @param title    The title search query
    * @param ownerId  The ID of the note owner
    * @param pageable Pagination information
    * @return {@link Page} of matching notes
    */
   Page<Note> searchByTitle(String title, Long ownerId, Pageable pageable);

   /**
    * Performs semantic search on notes using vector embeddings.
    *
    * @param query    The natural language search query
    * @param ownerId  The ID of the note owner
    * @param pageable Pagination information
    * @return {@link Page} of semantically matching notes
    */
   Page<Note> semanticSearch(String query, Long ownerId, Pageable pageable);

   /**
    * Finds a note by its path.
    *
    * @param path The unique path identifier
    * @return {@link Optional} containing the note DTO or empty if not found
    */
   Optional<NoteDto> findByPath(String path);

   /**
    * Finds a note by its ID.
    *
    * @param id The note ID
    * @return {@link Optional} containing the note DTO or empty if not found
    */
   Optional<NoteDto> findById(Long id);

   /**
    * Retrieves comments for a specific note with pagination.
    *
    * @param noteId   The ID of the parent note
    * @param pageable Pagination information
    * @return {@link Page} of comment DTOs
    */
   Page<NoteDto> findComments(Long noteId, Pageable pageable);
}
