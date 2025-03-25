package com.notes.models.dto.note;

import com.notes.models.NoteType;
import com.notes.models.SyntaxType;
import lombok.NonNull;

public record CommentEditResponseDto(@NonNull String title,
                                     @NonNull String path,
                                     @NonNull String content,
                                     @NonNull SyntaxType syntaxType,
                                     @NonNull NoteType noteType,
                                     boolean isPublic)
        implements Content, Title, ISyntaxType, INoteType, IsPublic, Path {
}
