package com.notes.services.managers;

import com.notes.exceptions.AccountNotFoundException;
import com.notes.mappers.repository.AccountRepositoryMapper;
import com.notes.models.domain.AccountDomainDto;
import com.notes.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountInformationService {
   private final AccountRepository accountRepository;
   private final AccountRepositoryMapper accountRepositoryMapper;

   /**
    * Finds an account by name with registration check and ownership validation.
    *
    * @param name The account name to search for
    * @return AccountDomainDto with account details
    * @throws AccountNotFoundException if account not found
    * @PreAuthorize Ensures account registration through AccountRegisterService
    * @PostAuthorize Verifies returned account matches authenticated principal's UUID
    */
   //TODO: не следует из сигнатуры, нигде не указано, является бизнес логикой и пишет в бд
   @PreAuthorize("@accountRegisterService.registerIfAbsent(#name, authentication.principal)")
   @PostAuthorize("returnObject.uuid == authentication.principal.uuid")
   public AccountDomainDto findAccount(String name) {
      return unsafeFindAccount(name);
   }

   /**
    * Retrieves account by ID with ownership validation.
    *
    * @param id The account ID to retrieve
    * @return AccountDomainDto with account details
    * @throws AccountNotFoundException if account not found
    * @PostAuthorize Verifies returned account matches authenticated principal's UUID
    */
   @PostAuthorize("returnObject.uuid == authentication.principal.uuid")
   public AccountDomainDto getAccount(Long id) {
      return unsafeGetAccount(id);
   }

   /**
    * Retrieves account by ID without security checks.
    *
    * @param id The account ID to retrieve
    * @return AccountDomainDto with account details
    * @throws AccountNotFoundException if account not found
    */
   public AccountDomainDto unsafeGetAccount(Long id) {
      var account = accountRepository.findById(id)
              .orElseThrow(AccountNotFoundException::new);
      return accountRepositoryMapper.of(account);
   }

   /**
    * Finds account by name without security checks.
    *
    * @param name The account name to search for
    * @return AccountDomainDto with account details
    * @throws AccountNotFoundException if account not found
    */
   public AccountDomainDto unsafeFindAccount(String name) {
      var account = accountRepository.findByName(name)
              .orElseThrow(AccountNotFoundException::new);
      return accountRepositoryMapper.of(account);
   }
}
