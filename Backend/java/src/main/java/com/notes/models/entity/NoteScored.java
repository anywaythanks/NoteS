package com.notes.models.entity;

import java.math.BigDecimal;

public record NoteScored(Note note, BigDecimal score) {
}
