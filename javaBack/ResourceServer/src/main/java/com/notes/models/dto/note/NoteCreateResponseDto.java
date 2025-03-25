package com.notes.models.dto.note;

import com.notes.models.NoteType;
import com.notes.models.SyntaxType;
import lombok.NonNull;

public record NoteCreateResponseDto(@NonNull String description,
                                    @NonNull String content,
                                    @NonNull String title,
                                    @NonNull String path,
                                    @NonNull SyntaxType syntaxType,
                                    @NonNull NoteType noteType,
                                    boolean isPublic) implements Content, Description, Title, ISyntaxType, INoteType, IsPublic, Path {
}
