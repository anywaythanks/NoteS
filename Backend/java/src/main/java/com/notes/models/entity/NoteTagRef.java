package com.notes.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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

import static jakarta.persistence.GenerationType.IDENTITY;
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
public class NoteTagRef {
   @EmbeddedId
   @GeneratedValue(strategy = IDENTITY)
   NoteTagId id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_note", nullable = false, insertable = false, updatable = false)
   Note note;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_tag", nullable = false, insertable = false, updatable = false)
   Tag tag;

   @Getter
   @AllArgsConstructor
   @NoArgsConstructor(access = PROTECTED)
   @Embeddable
   public static class NoteTagId {
      @Column(name = "id_note")
      protected Long noteId;
      @Column(name = "id_tag")
      protected Long tagId;
   }
}
