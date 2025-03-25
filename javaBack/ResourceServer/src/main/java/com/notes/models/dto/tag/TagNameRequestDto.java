package com.notes.models.dto.tag;

import lombok.NonNull;

public record TagNameRequestDto(@NonNull String name)
        implements Name {
}
