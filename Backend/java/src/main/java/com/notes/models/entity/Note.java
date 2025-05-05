package com.notes.models.entity;

import com.notes.converters.NoteTypeConverter;
import com.notes.converters.StateConverter;
import com.notes.listeners.NoteListener;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DynamicUpdate
@Table(name = "notes")
@NamedEntityGraph(name = "Note.owner",
        attributeNodes = {@NamedAttributeNode("owner")})
@NamedEntityGraph(name = "Note.actual.partial",
        attributeNodes = {@NamedAttributeNode("owner"),
                @NamedAttributeNode(value = "actual", subgraph = "subgraph.partial")},
        subgraphs = {@NamedSubgraph(name = "subgraph.partial",
                attributeNodes = {@NamedAttributeNode("title"),
                        @NamedAttributeNode("description"),
                        @NamedAttributeNode("syntaxType")})})
@NamedEntityGraph(name = "Note.actual.full",
        attributeNodes = {@NamedAttributeNode(value = "actual"), @NamedAttributeNode("owner")})
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@EntityListeners(NoteListener.class)
@SQLRestriction("not (state_id = 3 or state_id = 4)")
public class Note {
   @Id
   @GeneratedValue(strategy = SEQUENCE, generator = "note_seq")
   @SequenceGenerator(name = "note_seq", sequenceName = "note_id_seq")
   Long id;

   @NotNull
   @NotEmpty
   @Length(max = 128)
   @Column(name = "path", nullable = false, unique = true)
   String path;

   @NotNull
   @Column(name = "elastic_uuid", nullable = false, unique = true)
   UUID elasticUuid;

   @NotNull
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "account_id", nullable = false)
   Account owner;

   @Column(name = "type", nullable = false)
   @Convert(converter = NoteTypeConverter.class)
   @Setter
   NoteType noteType;

   @NotNull
   @Column(name = "is_public", nullable = false)
   @Setter
   Boolean isPublic;

   @Column(name = "commit_to", insertable = false, updatable = false)
   @Setter
   Long commitTo;

   @NotNull
   @OneToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "commit_to", nullable = false, unique = true)
   @Setter
   Commit actual;

   @Column(name = "state_id", nullable = false)
   @Convert(converter = StateConverter.class)
   @Setter
   State state;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "main_note", nullable = false)
   Note mainNote;

   @Column(name = "created_on", nullable = false)
   @Setter
   Instant createdOn;
}