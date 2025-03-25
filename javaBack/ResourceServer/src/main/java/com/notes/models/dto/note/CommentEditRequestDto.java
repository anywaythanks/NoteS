package com.notes.models.dto.note;

import com.notes.models.SyntaxType;
import lombok.NonNull;

public record CommentEditRequestDto(@NonNull String content,
                                    @NonNull String title,
                                    @NonNull SyntaxType syntaxType) implements Content, Title, ISyntaxType {
}
