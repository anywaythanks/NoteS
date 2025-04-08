package com.notes.services.managers;

import com.notes.exceptions.AccountNotFoundException;
import com.notes.models.auth.UserPrincipal;
import com.notes.models.entity.Account;
import com.notes.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service handling account registration and existence checks.
 * Manages account creation and validation during user registration flows.
 */
@Service
@RequiredArgsConstructor
public class AccountRegisterService {
   private final AccountRepository accountRepository;

   /**
    * Retrieves an account by name, throwing an exception if not found.
    *
    * @param name The name of the account to retrieve
    * @return The found {@link Account} entity
    * @throws AccountNotFoundException If no account exists with the given name
    */
   public Account getAccount(String name) {
      return accountRepository.findByName(name)
              .orElseThrow(AccountNotFoundException::new);
   }

   /**
    * Internal method for registering a new account (currently partially implemented).
    *
    * @param accountName The name for the new account
    * @param uuid        The UUID for the new account
    * @return The created {@link Account} entity
    */
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

   /**
    * Registers an account if it doesn't exist, validating principal consistency.
    *
    * @param name The account name to check/register
    * @param user The authenticated user principal
    * @return Always returns true after potential registration
    */
   public boolean registerIfAbsent(String name, UserPrincipal user) {
      if(Objects.equals(name, user.getUsername()) &&
         accountRepository.findByUuid(user.getUuid()).isEmpty()) {
         register(user.getUsername(), user.getUuid());
      }
      return true;
   }
}
