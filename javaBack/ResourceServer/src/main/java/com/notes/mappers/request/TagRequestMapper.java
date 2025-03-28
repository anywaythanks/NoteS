package com.notes.mappers.request;

import com.notes.configs.MapstructConfig;
import com.notes.models.api.tag.TagCreateRequestDto;
import com.notes.models.domain.TagCreateDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface TagRequestMapper {
   TagCreateDto of(TagCreateRequestDto tag);
}
