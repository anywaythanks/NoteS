using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using NoteS.models;

namespace NoteS.Models;

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
    public List<Tag> Tags { get; set; } = []; //many-to-many

    [Column("type")] public NoteTypes? Type { get; set; }
    [Column("is_public")] public bool IsPublic { get; set; }
    [Column("prev")] public Note? MainNote { get; set; } //поскольку это дерево, то не следует вовсе заполнять

    [Column("syntax_type_id")] public SyntaxType? SyntaxType { get; set; }

    //https://ru.stackoverflow.com/questions/1416392/%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0-cannot-write-datetime-with-kind-local-to-postgresql-type-timestamp-with
    [Column("created_on")] public DateTime? CreatedOn { get; set; }

    public bool IsEditable()
    {
        var betw = CreatedOn - DateTime.Now;
        if (betw == null || betw.Value.CompareTo(TimeSpan.FromDays(1)) > 0) return false;
        return true;
    }
}