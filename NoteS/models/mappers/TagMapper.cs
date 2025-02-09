using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target)]
public partial class TagMapper
{
    [MapProperty("Id", "Val")]
    public partial Field<ITagId, int> ToId(Tag tag);

    [MapProperty("Name", "Val")]
    public partial Field<ITagName, string> ToName(Tag tag);

    [MapProperty("Tag", "Val")]
    public partial Field<ITagName, string> Of(NoteSearchTagsRequestDto path);

    [MapProperty("Name", "Val")]
    public partial Field<ITagName, string> Of(CreateTagRequestDto path);

    [MapProperty("Name", "Val")]
    public partial Field<ITagName, string> Of(DeleteTagRequestDto path);
}