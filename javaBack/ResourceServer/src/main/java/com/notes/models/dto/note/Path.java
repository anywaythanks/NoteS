package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

interface Path {
    @NotNull
    String path();
}
