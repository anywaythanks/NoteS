package com.notes.models.api.account;

import jakarta.validation.constraints.NotNull;

interface Name {
   @NotNull
   String name();
}
