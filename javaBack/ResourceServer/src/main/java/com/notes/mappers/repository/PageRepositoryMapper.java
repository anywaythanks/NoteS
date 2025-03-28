package com.notes.mappers.repository;

import com.notes.configs.MapstructConfig;
import com.notes.models.domain.PageDomainDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(config = MapstructConfig.class)
public interface PageRepositoryMapper {
   default <T> PageDomainDto<T> of(Page<T> page) {
      return new PageDomainDto<>(page.toList(),
              page.getTotalPages(),
              page.getTotalElements(),
              page.getNumber());
   }
}
