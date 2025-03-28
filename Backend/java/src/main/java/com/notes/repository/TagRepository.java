package com.notes.repository;

import com.notes.models.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {
   @Query("from Tag t where t.owner.id = :ownerId")
   List<Tag> findByOwner(@Param("ownerId") Long ownerId);

   List<Tag> findByName(String name);

   @Query("from Tag t where t.owner.id = :ownerId and t.name = :name")
   Optional<Tag> find(@Param("ownerId") Long ownerId, String name);

   @Query("from Tag t where t.name in :names and t.owner.id = :ownerId")
   List<Tag> getTags(List<String> names, Long ownerId);
}