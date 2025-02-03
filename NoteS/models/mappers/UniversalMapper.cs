using NoteS.Models;
using NoteS.models.dto;
using Riok.Mapperly.Abstractions;

namespace NoteS.models.mappers;

[Mapper(RequiredMappingStrategy = RequiredMappingStrategy.Target)]
public partial class UniversalMapper
{
    public partial NoteRegisterResponseDto Of(Note destination);
    
}