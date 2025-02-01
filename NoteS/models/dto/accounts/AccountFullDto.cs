namespace NoteS.models.dto.accounts;

public class AccountFullDto(int? id, string name)
{
    public string Name { get; } = name; //UK
    public int? Id { get; } = id; //UK
}