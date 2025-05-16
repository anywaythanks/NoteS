package com.notes.models.api.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum StateApiDto {
   @JsonProperty("PENDING_CREATE")
   PENDING_CREATE,
   @JsonProperty("ACTIVE")
   ACTIVE,
   @JsonProperty("PENDING_MODIFY")
   PENDING_MODIFY,
   @JsonProperty("ACTIVE_MODIFIED")
   ACTIVE_MODIFIED,
   @JsonProperty("FAILED")
   FAILED,
   @JsonProperty("PENDING_ARCHIVE")
   PENDING_ARCHIVE,
   @JsonProperty("ARCHIVED")
   ARCHIVED;
}
