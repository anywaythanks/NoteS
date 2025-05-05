package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.notes.mappers.repository.NoteRepositoryMapper;
import com.notes.models.entity.Account_;
import com.notes.models.entity.Note;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.NoteScored;
import com.notes.models.entity.NoteTagRef;
import com.notes.models.entity.NoteTagRef_;
import com.notes.models.entity.NoteType;
import com.notes.models.entity.Note_;
import com.notes.models.entity.Tag_;
import com.notes.repository.NoteRepositorySearchQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class NoteRepositorySearchQueryImpl implements NoteRepositorySearchQuery {
   private final NoteRepositoryElastic elastic;
   private final NoteRepositoryMapper noteRepositoryMapper;
   private final NoteRepositoryDb db;

   @Override
   public Page<Note> findByTags(List<Long> tags,
                                List<Long> filterTags,
                                Long ownerId,
                                boolean isStrong,
                                Pageable pageable) {
//      var page = db.findAll((root, query, cb) -> {
//         var note = Objects.requireNonNull(query).from(Note.class);
//
//         var byOwnerId = cb.equal(note.get(Note_.owner).get(Account_.id), ownerId);
//         var byNoteType = cb.equal(note.get(Note_.noteType), NoteType.NOTE);
//         var result = cb.and(byOwnerId, byNoteType);
//         if(!tags.isEmpty()) {
//            var r = gen(tags, query, cb, note);
//            var tagSubquery = r.subquery;
//            var tagRef = r.root;
//            if(isStrong) {
//               result = cb.and(result, cb.equal(cb.countDistinct(tagRef.get(NoteTagRef_.tag)), tags.size()));
//            } else {
//               result = cb.and(result, cb.exists(tagSubquery));
//            }
//            result = cb.and(result, cb.exists(tagSubquery));
//         }
//
//         if(!filterTags.isEmpty()) {
//            var filterSubquery = gen(filterTags, query, cb, note).subquery;
//            result = cb.and(result, cb.not(cb.exists(filterSubquery)));
//         }
//         return result;
//      }, pageable);

      return db.findAll((root, query, cb) -> {
         // Base predicates

         Objects.requireNonNull(query);
         Predicate ownerPredicate = cb.equal(root.get(Note_.owner).get(Account_.id), ownerId);
         Predicate noteTypePredicate = cb.equal(root.get(Note_.noteType), NoteType.NOTE);
         List<Predicate> predicates = new ArrayList<>(Arrays.asList(ownerPredicate, noteTypePredicate));

         // Handle tags condition
         if (!tags.isEmpty()) {
            if (isStrong) {
               // AND logic: must have all tags
               Subquery<Long> tagSubquery = query.subquery(Long.class);
               Root<NoteTagRef> tagRefRoot = tagSubquery.from(NoteTagRef.class);
               tagSubquery.select(tagRefRoot.get(NoteTagRef_.note).get(Note_.id))
                       .where(cb.and(
                               cb.equal(tagRefRoot.get(NoteTagRef_.note).get(Note_.id), root.get(Note_.id)),
                               tagRefRoot.get(NoteTagRef_.tag).get(Tag_.id).in(tags)
                       ))
                       .groupBy(tagRefRoot.get(NoteTagRef_.note).get(Note_.id))
                       .having(cb.equal(cb.countDistinct(tagRefRoot.get(NoteTagRef_.tag)), tags.size()));
               predicates.add(cb.exists(tagSubquery));
            } else {
               // OR logic: must have at least one tag
               Subquery<Long> tagSubquery = query.subquery(Long.class);
               Root<NoteTagRef> tagRefRoot = tagSubquery.from(NoteTagRef.class);
               tagSubquery.select(tagRefRoot.get(NoteTagRef_.note).get(Note_.id))
                       .where(cb.and(
                               cb.equal(tagRefRoot.get(NoteTagRef_.note).get(Note_.id), root.get(Note_.id)),
                               tagRefRoot.get(NoteTagRef_.tag).get(Tag_.id).in(tags)
                       ));
               predicates.add(cb.exists(tagSubquery));
            }
         }

         // Handle filterTags condition
         if (!filterTags.isEmpty()) {
            Subquery<Long> filterSubquery = query.subquery(Long.class);
            Root<NoteTagRef> filterRefRoot = filterSubquery.from(NoteTagRef.class);
            filterSubquery.select(filterRefRoot.get(NoteTagRef_.note).get(Note_.id))
                    .where(cb.and(
                            cb.equal(filterRefRoot.get(NoteTagRef_.note).get(Note_.id), root.get(Note_.id)),
                            filterRefRoot.get(NoteTagRef_.tag).get(Tag_.id).in(filterTags)
                    ));
            predicates.add(cb.not(cb.exists(filterSubquery)));
         }

         return cb.and(predicates.toArray(new Predicate[0]));
      }, pageable, new NamedEntityGraph(EntityGraphType.FETCH, "Note.actual.partial"));
   }

   private record Result<S, R>(Subquery<S> subquery, Root<R> root) {
   }

   private static Result<Long, NoteTagRef> gen(List<Long> tags, CriteriaQuery<?> query, CriteriaBuilder cb, Root<Note> note) {
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<NoteTagRef> ref = subquery.from(NoteTagRef.class);
      subquery.select(ref.get(NoteTagRef_.note).get(Note_.id))
              .where(cb.and(
                      cb.equal(ref.get(NoteTagRef_.note).get(Note_.id), note.get(Note_.id)),
                      ref.get(NoteTagRef_.tag).get(Tag_.id).in(tags)
              ));
      return new Result<>(subquery, ref);
   }

   @Override
   public Page<NoteScored> semanticSearch(String query, Long ownerId, Pageable pageable) {
      var uuids = elastic.semanticSearch(query, ownerId, pageable);
      return map(uuids);
   }

   @Override
   public Page<NoteScored> searchByTitle(String title, Long ownerId, Pageable pageable) {
      var uuids = elastic.searchByTitle(title, ownerId, pageable);
      return map(uuids);
   }


   private Page<NoteScored> map(Page<NoteContent> page) {
      var uuids = page.stream()
              .map(NoteContent::getUuid)
              .toList();

      var result = db.findByElasticUuids(uuids);
      var r = StreamSupport.stream(result.spliterator(), false)
              .collect(Collectors.toMap(Note::getElasticUuid, Function.identity()));

      return page.map(noteContent -> noteRepositoryMapper.of(r.get(noteContent.getUuid()), noteContent));
   }
}
