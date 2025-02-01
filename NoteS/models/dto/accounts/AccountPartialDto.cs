using System.ComponentModel.DataAnnotations;

namespace NoteS.models.dto.accounts;

public class AccountPartialDto(string name)
{
    [Required] [MaxLength(64)] public string Name { get; } = name; //UK
}