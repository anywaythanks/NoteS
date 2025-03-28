package com.notes.models.api.note;

import lombok.NonNull;

public record NoteSemanticSearchRequestDto(@NonNull String query) implements Query {
}
