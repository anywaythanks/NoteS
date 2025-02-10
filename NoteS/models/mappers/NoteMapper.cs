using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target)]
public partial class NoteMapper
{
    [MapProperty("Id", "Val")]
    public partial Field<INoteId, int> ToId(Note note);

    [MapProperty("Title", "Val")]
    public partial Field<INoteTitle, string> ToTitle(Note note);

    [MapProperty("ElasticUuid", "Val")]
    public partial Field<INoteElasticUid, string> ToUid(Note note);

    [MapProperty("Path", "Val")]
    public partial Field<INotePath, string> ToPath(Note note);

    [MapProperty("PathNote", "Val")]
    public partial Field<INotePath, string> Of(NotePath path);

    [MapProperty("Title", "Val")]
    public partial Field<INoteTitle, string> Of(NoteSearchRequestDto path);

    [MapProperty("Content", "Val")]
    public partial Field<INoteContent, string> ToContent(Note note);

    [MapProperty("IsPublic", "Val")]
    public partial Field<INoteIsPublic, bool> ToIsPublic(Note note);
}