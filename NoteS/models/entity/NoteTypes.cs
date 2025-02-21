using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics.CodeAnalysis;
using System.Diagnostics.Contracts;
using System.Text.Json.Serialization;

namespace NoteS.models.entity;

[Table("note_types")]
public class NoteTypes
{
    [Column("id")] [Key] public int Num { get; init; }
    [Column("name")] public NoteTypeName Name { get; init; }
    public static readonly NoteTypes Note = new(0, NoteTypeName.NOTE);
    public static readonly NoteTypes Comment = new(1, NoteTypeName.COMMENT);
    public static readonly NoteTypes CommentRedacted = new(2, NoteTypeName.COMMENT_REDACTED);

    [Pure]
    public static bool IsComment(NoteTypes? type)
    {
        return type?.Name is NoteTypeName.COMMENT_REDACTED or NoteTypeName.COMMENT;
    }

    public static int? TypeToNum(NoteTypes? noteType)
    {
        return noteType?.Num;
    }

    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum NoteTypeName
    {
        NOTE,
        COMMENT,
        COMMENT_REDACTED
    }

    [return: NotNullIfNotNull(nameof(num))]
    public static NoteTypes? NumToType(int? num)
    {
        if (num == null) return null;
        switch (num)
        {
            case 0: return Note;
            case 1: return Comment;
            case 2: return CommentRedacted;
        }

        throw new ArgumentException(); //TODO: другая ошибка
    }

    public static NoteTypes NameToType(NoteTypeName syntaxType)
    {
        switch (syntaxType)
        {
            case NoteTypeName.NOTE: return Note;
            case NoteTypeName.COMMENT: return Comment;
        }

        throw new ArgumentException();
    }

    protected NoteTypes()
    {
    }

    protected NoteTypes(int num, NoteTypeName name)
    {
        Num = num;
        Name = name;
    }
}