package com.notes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tags_notes_map")
@NamedEntityGraph(name = "NoteTagRef.note",
        attributeNodes = @NamedAttributeNode("note"))
@NamedEntityGraph(name = "NoteTagRef.tag",
        attributeNodes = @NamedAttributeNode("tag"))
@NamedEntityGraph(name = "NoteTagRef.detail",
        attributeNodes = {@NamedAttributeNode("note"),
                @NamedAttributeNode("tag")})
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@IdClass(NoteTagRef.NoteTagId.class)
public class NoteTagRef {
   @Getter
   @AllArgsConstructor
   @NoArgsConstructor(access = PROTECTED)
   static class NoteTagId {
      protected Integer note;
      protected Integer tag;
   }

   @Id
   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_note", nullable = false)
   Note note;

   @Id
   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_tag", nullable = false)
   Tag tag;
}
