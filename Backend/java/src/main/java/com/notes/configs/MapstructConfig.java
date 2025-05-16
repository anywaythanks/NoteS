package com.notes.configs;

import com.notes.mappers.repository.AccountRepositoryMapper;
import com.notes.mappers.repository.EntryRepositoryMapper;
import com.notes.mappers.repository.PageRepositoryMapper;
import com.notes.mappers.repository.TagRepositoryMapper;
import com.notes.mappers.request.NoteRequestMapper;
import com.notes.mappers.request.TagRequestMapper;
import com.notes.mappers.response.NoteResponseMapper;
import com.notes.mappers.response.PageResponseMapper;
import com.notes.mappers.response.TagResponseMapper;
import org.mapstruct.MapperConfig;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;
import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.SubclassExhaustiveStrategy.COMPILE_ERROR;

@MapperConfig(componentModel = "spring",
        injectionStrategy = CONSTRUCTOR,
        nullValueCheckStrategy = ALWAYS,
        nullValueMappingStrategy = RETURN_NULL,
        unmappedTargetPolicy = ERROR,
        subclassExhaustiveStrategy = COMPILE_ERROR,

        uses = {AccountRepositoryMapper.class, EntryRepositoryMapper.class,
                PageRepositoryMapper.class, TagRepositoryMapper.class,

                NoteRequestMapper.class, TagRequestMapper.class,

                NoteResponseMapper.class, PageResponseMapper.class, TagResponseMapper.class
        })
public interface MapstructConfig {
}
