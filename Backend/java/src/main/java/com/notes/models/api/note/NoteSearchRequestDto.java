package com.notes.models.api.note;

import lombok.NonNull;

public record NoteSearchRequestDto(@NonNull String title)
        implements Title {
}
