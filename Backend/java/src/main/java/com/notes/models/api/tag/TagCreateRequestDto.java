package com.notes.models.api.tag;

import lombok.NonNull;

public record TagCreateRequestDto(@NonNull String name,
                                  @NonNull Integer color)
        implements Color, Name {
}
