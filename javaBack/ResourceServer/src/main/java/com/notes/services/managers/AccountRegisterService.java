package com.notes.services.managers;

import com.notes.exceptions.AccountNotFoundException;
import com.notes.models.auth.UserPrincipal;
import com.notes.models.entity.Account;
import com.notes.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {
   private final AccountRepository accountRepository;

   public Account getAccount(String name) {
      return accountRepository.findByName(name)
              .orElseThrow(AccountNotFoundException::new);
   }

   private Account register(String accountName, String uuid) {
//      var accountFind = accountRepository.findByName(accountName)
//              .or(() -> accountRepository.findByUuid(uuid));

//      if (accountFind.isPresent()) {
//         if (!accountFind.get().getUuid().equals(uuid))
//            throw new AccountForbiddenException();
//         throw new AccountUniqueException();
//      }
      var account = Account.builder()
              .name(accountName)
              .uuid(uuid)
              .build();
      return accountRepository.save(account);
   }

   public boolean registerIfAbsent(String name, UserPrincipal user) {
      if (Objects.equals(name, user.getUsername()) &&
          accountRepository.findByUuid(user.getUuid()).isEmpty()) {
         register(user.getUsername(), user.getUuid());
      }
      return true;
   }
}
