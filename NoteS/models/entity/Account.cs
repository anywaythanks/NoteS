using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.models.entity;

[Table("accounts")]
public class Account(string name, string uid)
{
    [Column("id")] public int? Id { get; init; }

    [Column("name")] [MaxLength(128)] public string Name { get; init; } = name;

    [Column("uid")] [MaxLength(128)] public string Uid { get; init; } = uid;

    void test()
    {
        var f = new Field<IAccId, int>(1);
        var val = f.Val;
    }
}

public interface IAccId : ITypeMarker<int>;

public interface IAccName : ITypeMarker<string>;

public interface IAccUid : ITypeMarker<string>;