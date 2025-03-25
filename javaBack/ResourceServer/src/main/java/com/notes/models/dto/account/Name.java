package com.notes.models.dto.account;

import jakarta.validation.constraints.NotNull;

interface Name {
    @NotNull
    String name();
}
