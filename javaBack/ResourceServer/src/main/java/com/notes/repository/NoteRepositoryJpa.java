package com.notes.repository;

import com.notes.models.Note;
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
public interface NoteRepositoryJpa extends JpaRepository<Note, Long> {
   Optional<Note> findByPath(@NonNull String path);

   @Query("""
           from Note n where n.mainNote.id = :noteId
           and (n.noteType = #{T(com.notes.models.NoteType).COMMENT}
             or n.noteType = #{T(com.notes.models.NoteType).COMMENT_REDACTED})
           and n.isPublic = true
           order by n.id""")
   @EntityGraph(value = "Note.owner", type = EntityGraph.EntityGraphType.LOAD)
   Page<Note> findComments(@NonNull @Param("noteId") Integer noteId, Pageable pageable);

   @Query("""
           from Note n where n.owner.id = :ownerId
           and (n.noteType = #{T(com.notes.models.NoteType).NOTE})
           and n.isPublic = true
           order by n.id""")
   Page<Note> findNotesByOwner(@NonNull @Param("ownerId") Integer ownerId, Pageable pageable);


   @Query("""
           from Note n
               left join NoteTagRef ntr on ntr.note.id = n.id
           where n.owner.id = :ownerId
           and n.noteType = #{T(com.notes.models.NoteType).NOTE}
           and ntr.tag.id = all elements(:tags)
           and not ntr.tag.id in :filterTags
           order by n.id""")
   Page<Note> findStrongByTagId(@Param("tags") List<Integer> tags,
                                @Param("filterTags") List<Integer> filterTags,
                                @Param("ownerId") Integer ownerId,
                                Pageable pageable);

   @Query("""
           from Note n
               left join NoteTagRef ntr on ntr.note.id = n.id
           where n.owner.id = :ownerId
           and n.noteType = #{T(com.notes.models.NoteType).NOTE}
           and ntr.tag.id in :tags
           and not ntr.tag.id in :filterTags
           order by n.id""")
   Page<Note> findWeakByTagId(@Param("tags") List<Integer> tags,
                              @Param("filterTags") List<Integer> filterTags,
                              @Param("ownerId") Integer ownerId,
                              Pageable pageable);

   @Query("""
           from Note n
           where n.elasticUuid in :uuids
           order by n.id""")
   List<Note> findByElasticUuids(@Param("uuids") List<String> uuids);
}