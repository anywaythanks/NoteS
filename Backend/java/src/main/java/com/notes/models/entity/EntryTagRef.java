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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tags_notes_map")
@NamedEntityGraph(name = "EntryTagRef.entry",
        attributeNodes = @NamedAttributeNode("entry"))
@NamedEntityGraph(name = "EntryTagRef.tag",
        attributeNodes = @NamedAttributeNode("tag"))
@NamedEntityGraph(name = "EntryTagRef.detail",
        attributeNodes = {@NamedAttributeNode("entry"),
                @NamedAttributeNode("tag")})
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class EntryTagRef {
   @EmbeddedId
   @GeneratedValue(strategy = IDENTITY)
   EntryTagId id;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_note", nullable = false, insertable = false, updatable = false)
   Entry entry;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_tag", nullable = false, insertable = false, updatable = false)
   Tag tag;

   @Getter
   @AllArgsConstructor
   @NoArgsConstructor(access = PROTECTED)
   @Embeddable
   public static class EntryTagId {
      @Column(name = "id_note")
      protected Long entryId;
      @Column(name = "id_tag")
      protected Long tagId;
   }
}
