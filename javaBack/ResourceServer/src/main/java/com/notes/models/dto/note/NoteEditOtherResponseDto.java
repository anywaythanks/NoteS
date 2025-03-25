package com.notes.models.dto.note;

import com.notes.models.SyntaxType;
import lombok.NonNull;

public record NoteEditOtherResponseDto(@NonNull String description,
                                       @NonNull String title,
                                       @NonNull SyntaxType syntaxType) implements Description, Title, ISyntaxType {
}
