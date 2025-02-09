using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using NoteS.models;
using NoteS.models.entity;

namespace NoteS.models.entity;

[Table("notes")]
public class Note(string title, string elasticUuid)
{
    [Column("id")] public int? Id { get; set; }

    [Column("path")] [MaxLength(128)] public string? Path { get; set; } //TODO: дефолт значение = юид
    [Column("title")] [MaxLength(128)] public string Title { get; set; } = title;

    [Column("elastic_uuid")]
    [MaxLength(128)]
    public string ElasticUuid { get; set; } = elasticUuid;

    public string? Content { get; set; } //TODO: из эластика
    [Column("account_id")] public required Account Owner { get; set; }
    
    public List<NoteTag> Tags { get; set; } = []; //many-to-many
    
    [Column("type")] public NoteTypes? Type { get; set; }
    [Column("is_public")] public bool IsPublic { get; set; }
    [Column("prev")] public Note? MainNote { get; set; } //поскольку это дерево, то не следует вовсе заполнять

    [Column("syntax_type_id")] public SyntaxType? SyntaxType { get; set; }
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)] [Column("created_on")] public DateTime? CreatedOn { get; set; }

    public bool IsEditable()
    {
        var betw = CreatedOn - DateTime.Now;
        if (betw == null || betw.Value.CompareTo(TimeSpan.FromDays(1)) > 0) return false;
        return true;
    }
}
public interface INoteId : ITypeMarker<int>;

public interface INotePath : ITypeMarker<string>;
public interface INoteTitle : ITypeMarker<string>;
public interface INoteElasticUid : ITypeMarker<string>;
public interface INoteContent : ITypeMarker<string>;
public interface INoteIsPublic : ITypeMarker<bool>;