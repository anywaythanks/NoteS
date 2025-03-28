package com.notes.mappers.repository;

import com.notes.configs.MapstructConfig;
import com.notes.models.domain.TagDomainDto;
import com.notes.models.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface TagRepositoryMapper {
   TagDomainDto of(Tag tag);
}
