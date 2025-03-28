package com.notes.repository;

import com.notes.models.entity.Note;
import com.notes.models.entity.NoteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Инкапсулриует логику распределенных транзакций.
 */
@Repository
@Transactional
public interface NoteRepository {
   boolean delete(Note note);

   NoteDto save(NoteDto noteDto);

   Page<Note> searchByTitle(String title, Long ownerId, Pageable pageable);

   Page<Note> semanticSearch(String query, Long ownerId, Pageable pageable);

   Optional<NoteDto> findByPath(String path);

   Optional<NoteDto> findById(Long id);

   Page<NoteDto> findComments(Long noteId, Pageable pageable);
}
