using System.ComponentModel.DataAnnotations;

namespace LR.model.dto.user;

public class AccountRegisterDto(Role role, string password)
{
    public Role Role { get; } = role;
    [Required] [MaxLength(64)] public string Password  { get; set; } = password;
}