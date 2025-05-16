package com.notes.repository.inner;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.EntryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Profile("elastic")
public class NoteCustomRepositoryElasticImpl implements NoteCustomRepositoryElastic {
   private final ElasticsearchTemplate elasticsearchTemplate;
   @Value("${elastic-model}")
   private String modelId;

   @Override
   public SearchHits<NoteContent> searchByTitle(String title, Long ownerId, Pageable pageable) {
      NativeQuery nativeQuery = NativeQuery.builder()
              .withQuery(q -> q
                      .bool(b -> b
                              .must(
                                      Query.of(m -> m.queryString(qs -> qs.query("*%s*".formatted(title)).fields("title"))),
                                      Query.of(m -> m.term(t -> t.field("owner").value(ownerId))),
                                      Query.of(m -> m.term(t -> t.field("entry_type").value(EntryType.NOTE.getName())))
                              )
                      )
              )
              .withPageable(pageable)
              .build();

      return elasticsearchTemplate.search(nativeQuery, NoteContent.class);
   }

   @Override
   public SearchHits<NoteContent> semanticSearch(String query, Long ownerId, Pageable pageable) {
      NativeQuery nativeQuery = NativeQuery.builder()
              .withQuery(q -> q
                      .knn(k -> k

                              .field("vector.vector")
                              .numCandidates(100L)
                              .queryVectorBuilder(b -> b
                                      .textEmbedding(t -> t
                                              .modelId(modelId)
                                              .modelText(query)
                                      )
                              )
                              .filter(f -> f
                                      .bool(b -> b
                                              .must(
                                                      Query.of(m -> m.term(t -> t.field("owner").value(ownerId))),
                                                      Query.of(m -> m.term(t -> t.field("entry_type").value(EntryType.NOTE.getName())))
                                              )
                                      )
                              )
                      )
              )
              .withPageable(pageable)
              .build();
      return elasticsearchTemplate.search(nativeQuery, NoteContent.class);
   }
}
