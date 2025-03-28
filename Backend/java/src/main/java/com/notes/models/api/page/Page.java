package com.notes.models.api.page;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

interface Page {
   @Positive
   @NotNull
   Integer page();
}
