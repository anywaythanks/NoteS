package com.notes.models.dto.tag;

import lombok.NonNull;

public record CreateTagRequestDto(@NonNull String name,
                                  @NonNull Integer color)
        implements Color, Name {
}
