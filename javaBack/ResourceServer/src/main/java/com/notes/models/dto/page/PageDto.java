package com.notes.models.dto.page;

import lombok.NonNull;

import java.util.List;

public record PageDto<T>(@NonNull List<T> items,
                         @NonNull Integer totalPages,
                         @NonNull Integer total,
                         @NonNull Integer page) implements Items<T>, TotalPages, Total, Page {
}
