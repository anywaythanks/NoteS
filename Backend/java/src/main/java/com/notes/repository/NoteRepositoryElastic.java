package com.notes.repository;

import com.notes.models.entity.NoteContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NoteRepositoryElastic extends ElasticsearchRepository<NoteContent, String> {
   @Query("""
           "bool":{
              "must":[
                  {
                  "query_string":{
                     "query": "*#{#title}",
                  }
                  },
                 {
                   "term":{
                     "owner": "#{#ownerId}"
                   }
                 },
                  {
                   "term":{
                     "entry_type": "#{T(com.notes.models.NoteType).NOTE.name}"
                   }
                 }
               ]
           }""")
   Page<NoteContent> searchByTitle(@Param("title") String title,
                                   @Param("ownerId") Long ownerId,
                                   Pageable pageable);

   @Query("""
           "knn": {
                 "field": "vector.vector",
                 "k": 10,
                 "num_candidates": 100,
                  "query_vector_builder": {
                         "text_embedding": {
                             "model_id": "cointegrated__rubert-tiny2",
                             "model_text": "#{#query}"
                         }
                     },
                 "filter":{
                 "bool":{
                       "must":[
                          {
                            "term":{
                              "owner": "#{#ownerId}"
                            }
                          },
                           {
                            "term":{
                              "entry_type": "#{T(com.notes.models.NoteType).NOTE.name}"
                            }
                          }
                        ]
                    }
                 }
               }""")
   Page<NoteContent> semanticSearch(@Param("query") String query,
                                    @Param("ownerId") Long ownerId,
                                    Pageable pageable);
//   @Query("""
//           "terms": {
//                 "field": "_id",
//                 "terms": "#{#uuids}"
//               }""")
//   List<NoteContent> findByIds(@Param("uuids") List<String> uuids);
}