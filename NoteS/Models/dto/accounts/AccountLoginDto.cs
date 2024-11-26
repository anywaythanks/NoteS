using System.ComponentModel.DataAnnotations;

namespace LR.model.dto.user;

public class AccountLoginDto(string password)
{
    [Required] [MaxLength(64)] public string Password  { get; set; } = password;
}