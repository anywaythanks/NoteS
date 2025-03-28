package com.notes.models.api.tag;

import jakarta.validation.constraints.NotNull;

interface Name {
   @NotNull
   String name();
}
