package com.notes.models.api.note;

import com.notes.models.api.tag.TagResponseDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

interface Tags {
   @NotNull
   List<TagResponseDto> tags();
}
