package com.notes.models.dto.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

interface OwnerName {
    @NotNull
    @JsonProperty("owner_account_name")
    String ownerName();
}
