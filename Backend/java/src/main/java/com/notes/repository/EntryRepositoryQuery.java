package com.notes.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.notes.models.entity.Entry;
import com.notes.models.entity.Entry_;
import lombok.NonNull;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * JPA repository implementation for {@link Entry} entities with custom queries.
 * Provides advanced search capabilities and entity graph loading strategies.
 */
@Transactional
public interface EntryRepositoryQuery extends EntityGraphJpaRepository<Entry, Long>, EntryRepositorySearchQuery {
}