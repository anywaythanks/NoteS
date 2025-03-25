package com.notes.models.dto.note;

import lombok.NonNull;

public record NoteEditOtherRequestDto(@NonNull String description,
                                      @NonNull String content) implements Content, Description {
}
