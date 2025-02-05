using NoteS.Models;
using NoteS.models.dto;
using NoteS.models.entity;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target)]
public partial class UniversalMapper
{
    private partial NoteSearchResponseDto OfSearch(Note source);
    public partial List<NoteSearchResponseDto> OfSearch(List<Note> source);
    private partial NoteType OfType(NoteTypes type);
    public partial NoteSearchContentResponseDto OfContentSearch(Note source);

    private TagResponseDto Of(NoteTag nt)
    {
        return Of(nt.Tag);
    }

    private partial List<TagResponseDto> Of(List<NoteTag> nt);
    
    public partial List<NoteSearchContentResponseDto> OfCommentsSearch(List<Note> source);
    public partial NoteEditPublicResponseDto OfEdit(Note source);
    public partial NoteEditContentResponseDto OfEditContent(Note source);
    public partial CommentEditResponseDto OfEditComment(Note source);
    public partial NoteCreateResponseDto OfCreateNote(Note source);
    public partial CommentCreateResponseDto OfCreateComment(Note source);
    public partial TagResponseDto Of(Tag tag);
    public partial List<TagResponseDto> Of(List<Tag> tag);
}