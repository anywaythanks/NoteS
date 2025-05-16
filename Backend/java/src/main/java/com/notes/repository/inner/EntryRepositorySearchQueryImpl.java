package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.notes.mappers.repository.EntryRepositoryMapper;
import com.notes.models.entity.Account_;
import com.notes.models.entity.Entry;
import com.notes.models.entity.EntryScored;
import com.notes.models.entity.EntryTagRef;
import com.notes.models.entity.EntryTagRef_;
import com.notes.models.entity.EntryType;
import com.notes.models.entity.Entry_;
import com.notes.models.entity.NoteContent;
import com.notes.models.entity.Tag_;
import com.notes.repository.EntryRepositorySearchQuery;
import com.notes.services.utils.EntryUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class EntryRepositorySearchQueryImpl implements EntryRepositorySearchQuery {
   private final NoteCustomRepositoryElastic elastic;
   private final EntryRepositoryMapper entryRepositoryMapper;
   private final EntryRepositoryDb db;
   private final EntryUuidsRepositoryDb uuidsDb;
   private final EntryUtils entryUtils;

   @Override
   public Page<Entry> findByTags(List<Long> tags,
                                 List<Long> filterTags,
                                 Long ownerId,
                                 boolean isStrong,
                                 Pageable pageable) {
      return db.findAll((root, query, cb) -> {
         Objects.requireNonNull(query);
         Predicate ownerPredicate = cb.equal(root.get(Entry_.owner).get(Account_.id), ownerId);
         Predicate noteTypePredicate = cb.equal(root.get(Entry_.entryType), EntryType.NOTE);
         List<Predicate> predicates = new ArrayList<>(Arrays.asList(ownerPredicate, noteTypePredicate));

         if(!tags.isEmpty()) {
            Subquery<Long> tagSubquery = query.subquery(Long.class);
            Root<EntryTagRef> tagRefRoot = tagSubquery.from(EntryTagRef.class);
            if(isStrong) {
               tagSubquery.select(tagRefRoot.get(EntryTagRef_.entry).get(Entry_.id))
                       .where(cb.and(
                               cb.equal(tagRefRoot.get(EntryTagRef_.entry).get(Entry_.id), root.get(Entry_.id)),
                               tagRefRoot.get(EntryTagRef_.tag).get(Tag_.id).in(tags)
                       ))
                       .groupBy(tagRefRoot.get(EntryTagRef_.entry).get(Entry_.id))
                       .having(cb.equal(cb.countDistinct(tagRefRoot.get(EntryTagRef_.tag)), tags.size()));
            } else {
               tagSubquery.select(tagRefRoot.get(EntryTagRef_.entry).get(Entry_.id))
                       .where(cb.and(
                               cb.equal(tagRefRoot.get(EntryTagRef_.entry).get(Entry_.id), root.get(Entry_.id)),
                               tagRefRoot.get(EntryTagRef_.tag).get(Tag_.id).in(tags)
                       ));
            }
            predicates.add(cb.exists(tagSubquery));
         }

         if(!filterTags.isEmpty()) {
            Subquery<Long> filterSubquery = query.subquery(Long.class);
            Root<EntryTagRef> filterRefRoot = filterSubquery.from(EntryTagRef.class);
            filterSubquery.select(filterRefRoot.get(EntryTagRef_.entry).get(Entry_.id))
                    .where(cb.and(
                            cb.equal(filterRefRoot.get(EntryTagRef_.entry).get(Entry_.id), root.get(Entry_.id)),
                            filterRefRoot.get(EntryTagRef_.tag).get(Tag_.id).in(filterTags)
                    ));
            predicates.add(cb.not(cb.exists(filterSubquery)));
         }
         entryUtils.softDelPredicate(root, cb);
         return cb.and(predicates.toArray(Predicate[]::new));
      }, pageable, new NamedEntityGraph(EntityGraphType.FETCH, Entry_.GRAPH_ENTRY_ACTUAL_PARTIAL));
   }

   //from Entry e where e.path = :path
   private Specification<Entry> getPath(String path) {
      return (root, query, cb) -> cb.and(
              cb.equal(root.get(Entry_.PATH), path),
              entryUtils.softDelPredicate(root, cb)
      );
   }

   @Override
   public Optional<Entry> findByPath(String path) {
      return db.findOne(getPath(path), new NamedEntityGraph(EntityGraphType.LOAD, Entry_.GRAPH_ENTRY_ACTUAL_FULL));
   }


   @Override
   public Optional<Entry> findByPathMinimal(String path) {
      return db.findOne(getPath(path));
   }

   //from Entry e where e.mainEntry.id = :noteId and e.entryType = :#{T(com.notes.models.entity.EntryType).COMMENT} and e.isPublic = true order by e.id
   @Override
   public Page<Entry> findPublicParents(Long noteId, Pageable pageable) {
      Specification<Entry> spec = (root, query, cb) -> cb.and(
              cb.equal(root.get(Entry_.MAIN_ENTRY).get(Entry_.ID), noteId),
              cb.equal(root.get(Entry_.ENTRY_TYPE), EntryType.COMMENT),
              cb.isTrue(root.get(Entry_.IS_PUBLIC)),
              entryUtils.softDelPredicate(root, cb)
      );

      return db.findAll(spec, pageable, new NamedEntityGraph(EntityGraphType.LOAD, Entry_.GRAPH_ENTRY_ACTUAL_FULL));
   }

   //from Entry e where e.owner.id = :ownerId and (e.entryType = :#{T(com.notes.models.entity.EntryType).NOTE}) order by e.id
   @Override
   public Page<Entry> findByOwner(Long ownerId, Pageable pageable) {
      Specification<Entry> spec = (root, query, cb) -> cb.and(
              cb.equal(root.get(Entry_.OWNER).get(Account_.ID), ownerId),
              cb.equal(root.get(Entry_.ENTRY_TYPE), EntryType.NOTE),
              entryUtils.softDelPredicate(root, cb)
      );

      return db.findAll(spec, pageable, new NamedEntityGraph(EntityGraphType.LOAD, Entry_.GRAPH_ENTRY_ACTUAL_PARTIAL));
   }

   @Override
   public Page<EntryScored> semanticSearch(String query, Long ownerId, Pageable pageable) {
      var uuids = elastic.semanticSearch(query, ownerId, pageable);
      return map(uuids, pageable);
   }

   @Override
   public Page<EntryScored> searchByTitle(String title, Long ownerId, Pageable pageable) {
      var uuids = elastic.searchByTitle(title, ownerId, pageable);
      return map(uuids, pageable);
   }


   private Page<EntryScored> map(SearchHits<NoteContent> hits, Pageable pageable) {
      var uuids = hits.stream()
              .map(note -> note.getContent().getUuid())
              .toList();

      var result = uuidsDb.findByElasticUuids(uuids);
      var r = StreamSupport.stream(result.spliterator(), false)
              .collect(Collectors.toMap(Entry::getElasticUuid, Function.identity()));
      return SearchHitSupport
              .searchPageFor(hits, pageable)
              .map(note ->
                      entryRepositoryMapper.of(r.get(note.getContent().getUuid()),
                              BigDecimal.valueOf(note.getScore())));
   }
}
