package com.notes.models.dto.page;

import jakarta.validation.constraints.NotNull;

import java.util.List;

interface Items<T> {
    @NotNull
    List<T> items();
}
