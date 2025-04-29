package com.notes.repository;

import com.notes.models.entity.NoteScored;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteRepositorySearchQuery {
   Page<NoteScored> semanticSearch(String query,
                                   Long ownerId,
                                   Pageable pageable);

   Page<NoteScored> searchByTitle(String title,
                                  Long ownerId,
                                  Pageable pageable);
}