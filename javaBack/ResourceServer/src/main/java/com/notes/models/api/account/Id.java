package com.notes.models.api.account;

import jakarta.validation.constraints.NotNull;

interface Id {
   @NotNull
   Long getId();
}
