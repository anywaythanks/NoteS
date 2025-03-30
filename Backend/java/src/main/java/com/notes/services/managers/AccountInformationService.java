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

   //TODO: не следует из сигнатуры, нигде не указано, является бизнес логикой и пишет в бд
   @PreAuthorize("@accountRegisterService.registerIfAbsent(#name, authentication.principal)")
   @PostAuthorize("returnObject.uuid == authentication.principal.uuid")
   public AccountDomainDto findAccount(String name) {
      return unsafeFindAccount(name);
   }

   @PostAuthorize("returnObject.uuid == authentication.principal.uuid")
   public AccountDomainDto getAccount(Long id) {
      return unsafeGetAccount(id);
   }

   public AccountDomainDto unsafeGetAccount(Long id) {
      var account = accountRepository.findById(id)
              .orElseThrow(AccountNotFoundException::new);
      return accountRepositoryMapper.of(account);
   }

   public AccountDomainDto unsafeFindAccount(String name) {
      var account = accountRepository.findByName(name)
              .orElseThrow(AccountNotFoundException::new);
      return accountRepositoryMapper.of(account);
   }
}
