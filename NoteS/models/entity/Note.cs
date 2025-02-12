using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NoteS.models.entity;

[Table("notes")]
public partial class Note(string title, string elasticUuid)
{
    [Column("id")] public int? Id { get; set; }

    [Column("path")] [MaxLength(128)] public string? Path { get; set; }
    [Column("title")] [MaxLength(128)] public string Title { get; set; } = title;

    [Column("elastic_uuid")]
    [MaxLength(128)]
    public string ElasticUuid { get; set; } = elasticUuid;

    public Account? OwnerAccount { get; set; }
    public string? Content { get; set; } //TODO: из эластика
    [Column("account_id")] public required int Owner { get; set; }
    public List<Tag> Tags { get; set; } = []; //чисто ради удобства
    public List<NoteTag> TagsRel { get; set; } = []; //many-to-many

    [Column("type")] public NoteTypes? Type { get; set; }
    [Column("is_public")] public bool IsPublic { get; set; }
    [Column("prev")] public int? MainNote { get; set; } //поскольку это дерево, то не следует вовсе заполнять
    public Note? MainNoteObject { get; set; }
    [Column("syntax_type_id")] public SyntaxType? SyntaxType { get; set; }

    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    [Column("created_on")]
    public DateTime? CreatedOn { get; set; }

    public bool IsEditable()
    {
        var betw = CreatedOn - DateTime.Now;
        return betw != null && betw.Value.CompareTo(TimeSpan.FromDays(1)) <= 0;
    }
}