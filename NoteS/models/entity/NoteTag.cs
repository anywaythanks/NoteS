﻿using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;
using NoteS.Models;

namespace NoteS.models.entity;

[Table("tags_notes_map")]
[PrimaryKey(nameof(NoteId), nameof(TagId))]
public class NoteTag
{
    [Column("id_note")] public int NoteId { get; init; }

    [Column("id_tag")] public int TagId { get; init; }

    public Note Note { get; init; }
    public Tag Tag { get; init; }
}