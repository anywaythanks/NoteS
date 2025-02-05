using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics.CodeAnalysis;

namespace NoteS.models.entity;

[Table("note_types")]
public class NoteTypes
{
    [Column("id")] [Key] public int Num { get; init; }
    [Column("name")] public string Name { get; init; }
    public static readonly NoteTypes Note = new(0, "NOTE");
    public static readonly NoteTypes Comment = new(1, "COMMENT");

    public static int? TypeToNum(NoteTypes? noteType)
    {
        return noteType?.Num;
    }

    [return: NotNullIfNotNull(nameof(num))]
    public static NoteTypes? NumToType(int? num)
    {
        if (num == null) return null;
        switch (num)
        {
            case 0: return Note;
            case 1: return Comment;
        }

        throw new ArgumentException(); //TODO: другая ошибка
    }
    protected NoteTypes()
    {
    }
    
    protected NoteTypes(int num, string name)
    {
        Num = num;
        Name = name;
    }
}