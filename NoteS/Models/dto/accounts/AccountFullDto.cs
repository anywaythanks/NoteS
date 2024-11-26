using System.ComponentModel.DataAnnotations;

namespace LR.model.dto.user;

public class AccountFullDto(int? id, Role role, string name, decimal amount, string password)
{
    public Role Role { get; } = role;
    public string Name { get; } = name; //UK
    public int? Id { get; } = id; //UK

    public Decimal Amount { get; } = amount;
    public string Password  { get; set; } = password;
}