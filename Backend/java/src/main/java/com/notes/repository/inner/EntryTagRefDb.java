package com.notes.repository.inner;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.notes.models.entity.EntryTagRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EntryTagRefDb extends JpaRepository<EntryTagRef, EntryTagRef.EntryTagId>, EntityGraphJpaSpecificationExecutor<EntryTagRef> {
}