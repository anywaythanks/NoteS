package com.notes.repository;

import com.notes.models.Account;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
   Optional<Account> findByName(@NonNull String name);

   Optional<Account> findByUuid(@NonNull String uuid);
}