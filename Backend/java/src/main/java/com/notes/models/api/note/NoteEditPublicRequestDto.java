package com.notes.models.api.note;

public record NoteEditPublicRequestDto(boolean isPublic) implements IsPublic {
}
