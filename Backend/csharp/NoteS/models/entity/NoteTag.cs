using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace NoteS.models.entity;

[Table("tags_notes_map")]
[PrimaryKey(nameof(NoteId), nameof(TagId))]
public class NoteTag
{
    [Column("id_note")] public int NoteId { get; init; }

    [Column("id_tag")] public int TagId { get; init; }
}