package com.notes.models.api.note;

import lombok.NonNull;

public record NoteEditOtherRequestDto(@NonNull String description,
                                      @NonNull String title) implements Title, Description {
}
