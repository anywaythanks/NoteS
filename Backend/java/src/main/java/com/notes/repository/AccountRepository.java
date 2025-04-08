package com.notes.repository;

import com.notes.models.entity.Account;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * JPA repository for managing {@link Account} entities.
 * Provides basic CRUD operations and custom query methods.
 */
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
   /**
    * Finds an account by its name.
    *
    * @param name The name of the account to search for (non-null)
    * @return {@link Optional} containing the found account or empty if not found
    */
   Optional<Account> findByName(@NonNull String name);

   /**
    * Finds an account by its UUID.
    *
    * @param uuid The UUID of the account to search for (non-null)
    * @return {@link Optional} containing the found account or empty if not found
    */
   Optional<Account> findByUuid(@NonNull String uuid);
}