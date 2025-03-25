package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

interface Id {
    @NotNull
    Long id();
}
