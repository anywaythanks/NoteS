package com.notes.repository;

import com.notes.models.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EntryRepositorySchedule {
   List<Entry> findScheduleDelete();

   List<Entry> findScheduleModify();

   List<Entry> findScheduleCreate();
}