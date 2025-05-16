package com.notes.repository.inner;

import com.notes.models.entity.Entry;
import com.notes.models.entity.Entry_;
import com.notes.models.entity.State;
import com.notes.repository.EntryRepositorySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
@RequiredArgsConstructor
public class EntryRepositoryScheduleImpl implements EntryRepositorySchedule {
   private final EntryRepositoryDb db;

   //select * from notes where state_id = :#{T(com.notes.models.entity.State).PENDING_ARCHIVE.id}
   public List<Entry> findScheduleDelete() {
      return db.findAll(
              (root, _, cb) -> cb.equal(root.get(Entry_.STATE), State.PENDING_ARCHIVE),
              Sort.by(Sort.Direction.ASC, Entry_.ID)
      );
   }

   //from Entry where state = :#{T(com.notes.models.entity.State).PENDING_MODIFY}
   public List<Entry> findScheduleModify() {
      return db.findAll((root, _, cb) -> cb.equal(root.get(Entry_.STATE), State.PENDING_MODIFY),
              Sort.by(Sort.Direction.ASC, Entry_.ID)
      );
   }

   //from Entry where state = :#{T(com.notes.models.entity.State).PENDING_CREATE}
   public List<Entry> findScheduleCreate() {
      return db.findAll(
              (root, _, cb) -> cb.equal(root.get(Entry_.STATE), State.PENDING_CREATE),
              Sort.by(Sort.Direction.ASC, Entry_.ID)
      );
   }
}