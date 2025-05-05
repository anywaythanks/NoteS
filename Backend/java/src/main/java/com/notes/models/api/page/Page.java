package com.notes.models.api.page;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

interface Page {
   @PositiveOrZero
   @NotNull
   Integer page();
}
