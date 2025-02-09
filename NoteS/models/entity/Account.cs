using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.models.entity;

[Table("accounts")]
public class Account(string name, string uuid)
{
    [Column("id")] public int? Id { get; set; }

    [Column("name")] [MaxLength(128)] public string Name { get; set; } = name;

    [Column("uuid")] [MaxLength(128)] public string Uuid { get; set; } = uuid;
}

public interface IAccId : ITypeMarker<int>;

public interface IAccName : ITypeMarker<string>;

public interface IAccUid : ITypeMarker<string>;