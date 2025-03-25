package com.notes.models.dto.tag;

import jakarta.validation.constraints.NotNull;

interface Name {
    @NotNull
    String name();
}
