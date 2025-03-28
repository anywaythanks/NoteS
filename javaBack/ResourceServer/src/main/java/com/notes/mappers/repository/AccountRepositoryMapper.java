package com.notes.mappers.repository;

import com.notes.configs.MapstructConfig;
import com.notes.models.domain.AccountDomainDto;
import com.notes.models.entity.Account;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface AccountRepositoryMapper {
   AccountDomainDto of(Account account);
}
