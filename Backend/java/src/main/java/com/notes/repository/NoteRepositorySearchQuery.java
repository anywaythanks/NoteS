package com.notes.repository;

import com.notes.models.entity.Note;
import com.notes.models.entity.NoteScored;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepositorySearchQuery {
   Page<NoteScored> semanticSearch(String query,
                                   Long ownerId,
                                   Pageable pageable);

   Page<NoteScored> searchByTitle(String title,
                                  Long ownerId,
                                  Pageable pageable);
   Page<Note> findByTags(List<Long> tags,
                              List<Long> filterTags,
                              Long ownerId,
                              boolean isStrong,
                              Pageable pageable);
}