package com.notes.models.dto.note;

import com.notes.models.dto.tag.TagResponseDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

interface Tags {
    @NotNull
    List<TagResponseDto> tags();
}
