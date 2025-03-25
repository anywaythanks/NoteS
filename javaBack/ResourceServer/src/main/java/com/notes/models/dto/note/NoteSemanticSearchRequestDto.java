package com.notes.models.dto.note;

import lombok.NonNull;

public record NoteSemanticSearchRequestDto(@NonNull String query) implements Query {
}
