package com.notes.mappers.response;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.tag.TagResponseDto;
import com.notes.models.domain.TagDomainDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface TagResponseMapper {
   TagResponseDto of(TagDomainDto tag);
}
