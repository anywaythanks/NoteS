package com.notes.mappers;

import com.notes.configs.MapstructConfig;
import com.notes.models.Account;
import com.notes.models.dto.account.AccountIdDto;
import com.notes.models.dto.account.AccountPartialResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface AccountMapper {

    AccountIdDto toDto(Account account);
}
