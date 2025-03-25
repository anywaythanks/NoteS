package com.notes.models.dto.page;

import lombok.NonNull;

public record LimitDto(@NonNull Integer limit) implements Limit {
}
