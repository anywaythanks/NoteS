package com.notes.models.dto.note;

import com.notes.models.SyntaxType;
import lombok.NonNull;

public record NoteEditOnlyContentRequestDto(@NonNull SyntaxType syntaxType,
                                            @NonNull String content) implements Content, ISyntaxType {
}
