using NoteS.Models;
using NoteS.models.dto;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target)]
public partial class UniversalMapper
{
    public partial NoteRegisterResponseDto OfRegister(Note source);
    public partial NoteSearchResponseDto OfSearch(Note source);
    public partial List<NoteSearchResponseDto> OfSearch(List<Note> source);
    public partial NoteSearchContentResponseDto OfContentSearch(Note source);
    public partial List<NoteSearchContentResponseDto> OfCommentsSearch(List<Note> source);
    public partial NoteEditPublicResponseDto OfEdit(Note source);
    public partial NoteEditContentResponseDto OfEditContent(Note source);
    public partial CommentEditResponseDto OfEditComment(Note source);
    public partial NoteCreateResponseDto OfCreateNote(Note source);
    public partial CommentCreateResponseDto OfCreateComment(Note source);
    public partial TagResponseDto Of(Tag tag);
    public partial List<TagResponseDto> Of(List<Tag> tag);
}