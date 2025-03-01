﻿using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target,
    EnabledConversions = MappingConversionType.Constructor)]
public partial class TagMapper
{
    public partial TagNameDto OfCreate(CreateTagRequestDto path);
    public partial TagNameDto OfAdd(AddTagRequestDto path);
    public partial TagNameDto OfDelete(TagNameRequestDto path);

    public partial TagIdDto ToIdDto(Tag tag);
    public partial TagNameDto ToNameDto(Tag tag);

    public partial TagIdDto OfIdDto(int? id);

    public List<TagNameDto> Of(List<string> tags)
    {
        return tags.Select(name => new TagNameDto(name)).ToList();
    }
}