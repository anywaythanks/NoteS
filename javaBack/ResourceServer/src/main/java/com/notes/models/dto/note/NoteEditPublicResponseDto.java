package com.notes.models.dto.note;

import lombok.NonNull;


public record NoteEditPublicResponseDto(boolean isPublic,
                                        @NonNull String title) implements IsPublic, Title {
}
