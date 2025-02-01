using System.ComponentModel.DataAnnotations;

namespace NoteS.models.dto.accounts;

public class AccountLoginDto(string password)
{
    [Required] [MaxLength(64)] public string Password  { get; set; } = password;
}