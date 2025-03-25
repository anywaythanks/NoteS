package com.notes.models.dto.note;

import jakarta.validation.constraints.NotNull;

interface Title {
    @NotNull
    String title();
}
