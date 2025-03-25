package com.notes.models.dto.note;

public record NoteEditPublicRequestDto(boolean isPublic) implements IsPublic {
}
