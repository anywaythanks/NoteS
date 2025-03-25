package com.notes.models.dto.page;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

interface Page {
    @Positive
    @NotNull
    Integer page();
}
