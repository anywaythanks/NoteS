package com.notes.models.api.tag;

import lombok.NonNull;

public record AddTagRequestDto(@NonNull String name)
        implements Name {
}
