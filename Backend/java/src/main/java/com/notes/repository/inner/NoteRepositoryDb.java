package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.notes.models.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Один из внутренних репозиториев, нужный только для пары запросов.
 */
@Transactional
interface NoteRepositoryDb extends JpaRepository<Note, Long>, EntityGraphJpaSpecificationExecutor<Note> {
   @Query("""
           from Note n
           where n.elasticUuid in :uuids
           order by n.id""")
   @EntityGraph(value = "Note.actual.partial", type = EntityGraph.EntityGraphType.LOAD)
   Iterable<Note> findByElasticUuids(@Param("uuids") List<UUID> uuids);
}