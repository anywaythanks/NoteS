package com.notes.models.api.page;

import lombok.NonNull;

public record PageLimitDto(@NonNull Integer limit) implements Limit {
}
