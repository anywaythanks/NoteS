package com.notes.models.api.page;

import lombok.NonNull;

import java.util.List;

public record PageDto<T>(@NonNull List<T> items,
                         @NonNull Integer totalPages,
                         @NonNull Long totalElements,
                         @NonNull Integer page) implements Items<T>, TotalPages, Total, Page {
}
