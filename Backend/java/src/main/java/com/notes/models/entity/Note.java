package com.notes.models.entity;

import com.notes.converters.NoteTypeConverter;
import com.notes.converters.SyntaxTypeConverter;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "notes")
@NamedEntityGraph(name = "Note.owner",
        attributeNodes = {@NamedAttributeNode("owner")})
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@EntityListeners(NoteListener.class)
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
   @NotEmpty
   @Length(max = 2048)
   @Column(name = "description", nullable = false)
   @Setter
   String description;

   @NotNull
   @NotEmpty
   @Length(max = 128)
   @Column(name = "title", nullable = false)
   @Setter
   String title;

   @NotNull
   @NotEmpty
   @Length(max = 128)
   @Column(name = "elastic_uuid", nullable = false, unique = true)
   String elasticUuid;

   @NotNull
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "account_id", nullable = false)
   Account owner;

   @Column(name = "type", nullable = false)
   @Convert(converter = NoteTypeConverter.class)
   @Setter
   NoteType noteType;

   @NotNull
   @Column(name = "syntax_type_id", nullable = false)
   @Convert(converter = SyntaxTypeConverter.class)
   @Setter
   SyntaxType syntaxType;

   @NotNull
   @Column(name = "is_public", nullable = false)
   @Setter
   Boolean isPublic;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "prev", nullable = false)
   Note mainNote;

   @Column(name = "created_on", nullable = false)
   @Setter
   Instant createdOn;
}