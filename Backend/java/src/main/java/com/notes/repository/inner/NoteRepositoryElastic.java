package com.notes.repository.inner;

import com.notes.models.entity.NoteContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Внутренний репозиторий для работы с эластиком.
 */
@Transactional
interface NoteRepositoryElastic extends ElasticsearchRepository<NoteContent, String> {
   /**
    * Performs a full-text search by title with ownership filtering.
    *
    * @param title    The title search query
    * @param ownerId  The ID of the note owner
    * @param pageable Pagination information
    * @return {@link Page} of matching note contents
    */
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

   /**
    * Performs semantic search using vector embeddings with Russian language model.
    *
    * @param query    The natural language search query
    * @param ownerId  The ID of the note owner
    * @param pageable Pagination information
    * @return {@link Page} of semantically similar notes
    */
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
   /**
    * Retrieves documents by their Elasticsearch IDs.
    *
    * @param uuids List of Elasticsearch document IDs
    * @return List of matching NoteContent entities
    */
//   @Query("""
//           "terms": {
//                 "field": "_id",
//                 "terms": "#{#uuids}"
//               }""")
//   List<NoteContent> findByIds(@Param("uuids") List<String> uuids);
}