package com.notes.repository.inner;

import com.notes.models.entity.NoteContent;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!elastic")
public class NoteCustomRepositoryElasticStub implements NoteCustomRepositoryElastic {
   @Override
   public SearchHits<NoteContent> searchByTitle(String title, Long ownerId, Pageable pageable) {
      throw new UnsupportedOperationException("Not implemented yet");
   }

   @Override
   public SearchHits<NoteContent> semanticSearch(String query, Long ownerId, Pageable pageable) {
      throw new UnsupportedOperationException("Not implemented yet");
   }
}
