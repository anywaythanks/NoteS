package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface Title {
   @NotNull
   String title();
}
