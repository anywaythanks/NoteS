package com.notes.repository;

import com.notes.models.entity.Note;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface NoteRepositoryDb extends JpaRepository<Note, Long> {
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"mainNote.path"})
   Optional<Note> findByPath(@NonNull String path);

   @Query("""
           from Note n where n.mainNote.id = :noteId
           and (n.noteType = #{T(com.notes.models.entity.NoteType).COMMENT}
             or n.noteType = #{T(com.notes.models.entity.NoteType).COMMENT_REDACTED})
           and n.isPublic = true
           order by n.id""")
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findComments(@NonNull @Param("noteId") Long noteId, Pageable pageable);

   @Query("""
           from Note n where n.owner.id = :ownerId
           and (n.noteType = #{T(com.notes.models.entity.NoteType).NOTE})
           and n.isPublic = true
           order by n.id""")
   Page<Note> findNotesByOwner(@NonNull @Param("ownerId") Long ownerId, Pageable pageable);


   @Query("""
           from Note n
               left join NoteTagRef ntr on ntr.note.id = n.id
           where n.owner.id = :ownerId
           and n.noteType = #{T(com.notes.models.entity.NoteType).NOTE}
           and ntr.tag.id = all elements(:tags)
           and not ntr.tag.id in :filterTags
           order by n.id""")
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findStrongByTagId(@Param("tags") List<Long> tags,
                                @Param("filterTags") List<Long> filterTags,
                                @Param("ownerId") Long ownerId,
                                Pageable pageable);

   @Query("""
           from Note n
               left join NoteTagRef ntr on ntr.note.id = n.id
           where n.owner.id = :ownerId
           and n.noteType = #{T(com.notes.models.entity.NoteType).NOTE}
           and ntr.tag.id in :tags
           and not ntr.tag.id in :filterTags
           order by n.id""")
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findWeakByTagId(@Param("tags") List<Long> tags,
                              @Param("filterTags") List<Long> filterTags,
                              @Param("ownerId") Long ownerId,
                              Pageable pageable);

   @Query("""
           from Note n
           where n.elasticUuid in :uuids
           order by n.id""")
   Iterable<Note> findByElasticUuids(@Param("uuids") List<String> uuids);
}