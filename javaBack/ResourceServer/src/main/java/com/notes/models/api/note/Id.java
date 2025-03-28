package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface Id {
   @NotNull
   Long id();
}
