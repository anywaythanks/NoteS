package com.notes.repository.inner;

import com.notes.models.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Один из внутренних репозиториев, нужный только для пары запросов.
 */
@Transactional
interface NoteRepositoryDb extends JpaRepository<Note, Long> {
   @Query("""
           from Note n
           where n.elasticUuid in :uuids
           order by n.id""")
   Iterable<Note> findByElasticUuids(@Param("uuids") List<UUID> uuids);
}