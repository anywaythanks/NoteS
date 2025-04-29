package com.notes.repository;

import com.notes.models.entity.SagaLog;
import com.notes.models.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface SagaRepository extends JpaRepository<SagaLog, Long> {

}