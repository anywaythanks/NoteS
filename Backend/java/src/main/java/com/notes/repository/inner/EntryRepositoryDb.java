package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.notes.models.entity.Entry;
import com.notes.models.entity.Entry_;
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
interface EntryRepositoryDb extends JpaRepository<Entry, Long>, EntityGraphJpaSpecificationExecutor<Entry> {
}