package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface Description {
   @NotNull
   String description();
}
