package com.notes.models.api.note;

import jakarta.validation.constraints.NotNull;

interface Content {
   @NotNull
   String content();
}
