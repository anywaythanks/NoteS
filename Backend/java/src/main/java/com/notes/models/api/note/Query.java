package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface Query {
   @NotNull
   String query();
}
