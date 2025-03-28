package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface Path {
   @NotNull
   String path();
}
