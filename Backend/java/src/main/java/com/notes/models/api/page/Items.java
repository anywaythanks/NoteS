package com.notes.models.api.page;

import jakarta.validation.constraints.NotNull;

import java.util.List;

interface Items<T> {
   @NotNull
   List<T> items();
}
