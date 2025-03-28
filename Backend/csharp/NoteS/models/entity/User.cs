namespace NoteS.models.entity;

public class User(Policies policies, string login, string uid)
{
    public string Login { get; init; } = login;
    public string Uid { get; init; } = uid;
    public Policies Policies { get; init; } = policies;
}