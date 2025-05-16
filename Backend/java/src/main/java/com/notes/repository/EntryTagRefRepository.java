package com.notes.repository;

import com.notes.models.entity.EntryTagRef;
import com.notes.repository.inner.EntryTagRefCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA repository for managing {@link EntryTagRef} entities representing relationships between notes and tags.
 * Provides methods for querying tag-note associations with entity graph loading.
 */
@Transactional
public interface EntryTagRefRepository extends JpaRepository<EntryTagRef, EntryTagRef.EntryTagId>, EntryTagRefCustomRepository {
}