package com.notes.repository.inner;

import com.notes.models.entity.NoteContent;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.query.Param;


interface NoteCustomRepositoryElastic {
   /**
    * Performs a full-text search by title with ownership filtering.
    *
    * @param title    The title search query
    * @param ownerId  The ID of the note owner
    * @param pageable Pagination information
    * @return {@link Page} of matching note contents
    */
   SearchHits<NoteContent> searchByTitle(@Param("title") String title,
                                         @Param("ownerId") Long ownerId,
                                         Pageable pageable);

   /**
    * Performs semantic search using vector embeddings with Russian language model.
    *
    * @param query    The natural language search query
    * @param ownerId  The ID of the note owner
    * @param pageable Pagination information
    * @return {@link Page} of semantically similar notes
    */
   SearchHits<NoteContent> semanticSearch(@Param("query") String query,
                                          @Param("ownerId") Long ownerId,
                                          Pageable pageable);
}