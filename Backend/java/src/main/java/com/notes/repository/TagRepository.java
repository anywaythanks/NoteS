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
   /**
    * Finds tags belonging to a specific owner.
    *
    * @param ownerId The ID of the tag owner
    * @return List of tags associated with the owner
    */
   @Query("from Tag t where t.owner.id = :ownerId")
   List<Tag> findByOwner(@Param("ownerId") Long ownerId);

   /**
    * Finds tags by name across all owners.
    *
    * @param name The tag name to search
    * @return List of tags with matching names
    */
   List<Tag> findByName(String name);

   /**
    * Finds a specific tag by owner and name combination.
    *
    * @param ownerId The ID of the tag owner
    * @param name    The tag name to search
    * @return {@link Optional} containing the matching tag or empty
    */
   @Query("from Tag t where t.owner.id = :ownerId and t.name = :name")
   Optional<Tag> find(@Param("ownerId") Long ownerId, String name);

   /**
    * Retrieves multiple tags by names for a specific owner.
    *
    * @param names   List of tag names to search
    * @param ownerId The ID of the tag owner
    * @return List of tags matching the names and owner
    */
   @Query("from Tag t where t.name in :names and t.owner.id = :ownerId")
   List<Tag> getTags(List<String> names, Long ownerId);
}