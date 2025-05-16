package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface State {
   @NotNull
   StateApiDto state();
}
