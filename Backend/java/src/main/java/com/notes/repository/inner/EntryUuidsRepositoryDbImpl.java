package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain2.NamedEntityGraph;
import com.notes.models.entity.Entry;
import com.notes.models.entity.Entry_;
import com.notes.services.utils.EntryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional
public class EntryUuidsRepositoryDbImpl implements EntryUuidsRepositoryDb {
   private final EntryRepositoryDb db;
   private final EntryUtils entryUtils;

   @Override
   //from Entry e where e.elasticUuid in :uuids order by e.id
   public List<Entry> findByElasticUuids(List<UUID> uuids) {
      return db.findAll((root, query, cb) ->
                      cb.and(root.get(Entry_.elasticUuid).in(uuids), entryUtils.softDelPredicate(root, cb)),
              Sort.by(Sort.Direction.ASC, Entry_.ID),
              new NamedEntityGraph(EntityGraphType.LOAD, Entry_.GRAPH_ENTRY_ACTUAL_PARTIAL));
   }
}
