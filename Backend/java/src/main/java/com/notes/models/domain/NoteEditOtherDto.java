package com.notes.models.domain;

import lombok.NonNull;

public record NoteEditOtherDto(@NonNull String description,
                               @NonNull String title) {
}
