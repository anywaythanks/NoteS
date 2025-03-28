package com.notes.models.domain;

import com.notes.models.api.note.Description;
import com.notes.models.api.note.Title;
import lombok.NonNull;

public record NoteEditOtherDto(@NonNull String description,
                               @NonNull String title) implements Title, Description {
}
