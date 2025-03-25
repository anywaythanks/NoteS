package com.notes.models.dto.note;

import lombok.NonNull;

public record NoteSearchRequestDto(@NonNull String title)
        implements Title {
}
