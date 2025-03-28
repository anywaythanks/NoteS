package com.notes.mappers.response;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.page.PageDto;
import com.notes.models.domain.PageDomainDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface PageResponseMapper {
   default <T> PageDto<T> of(PageDomainDto<T> page) {
      return new PageDto<>(page.items(),
              page.totalPages(),
              page.totalElements(),
              page.page());
   }
}
