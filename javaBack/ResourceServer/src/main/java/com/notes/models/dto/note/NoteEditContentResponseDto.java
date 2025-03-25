package com.notes.models.dto.note;

import com.notes.models.SyntaxType;
import lombok.NonNull;

public record NoteEditContentResponseDto(@NonNull String description,
                                         @NonNull String content,
                                         @NonNull String title,
                                         @NonNull SyntaxType syntaxType) implements Content, Description, Title, ISyntaxType {
}
