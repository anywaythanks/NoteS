package com.notes.models.dto.account;

import jakarta.validation.constraints.NotNull;

interface Id {
    @NotNull
    Long getId();
}
