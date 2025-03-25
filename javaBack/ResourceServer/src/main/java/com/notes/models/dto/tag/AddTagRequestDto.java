package com.notes.models.dto.tag;

import lombok.NonNull;

public record AddTagRequestDto(@NonNull String name)
        implements Name {
}
