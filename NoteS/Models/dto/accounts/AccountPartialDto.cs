using System.ComponentModel.DataAnnotations;

namespace LR.model.dto.user;

public class AccountPartialDto(Role role, string name, decimal amount)
{
    public Role Role { get; } = role;
    [Required] [MaxLength(64)] public string Name { get; } = name; //UK

    public Decimal Amount { get; init; } = amount;
}