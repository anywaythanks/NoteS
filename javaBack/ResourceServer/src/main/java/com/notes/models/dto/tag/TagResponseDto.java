package com.notes.models.dto.tag;

import lombok.NonNull;

public record TagResponseDto(@NonNull String name,
                             @NonNull Integer color)
        implements Color, Name {
}
