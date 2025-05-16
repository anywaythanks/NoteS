package com.notes.models.entity;

import com.notes.converters.SyntaxTypeConverter;
import com.notes.listeners.CommitListener;
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
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "commits")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@EntityListeners(CommitListener.class)
@NamedEntityGraph(name = "Commit.partial",
        attributeNodes = {@NamedAttributeNode("title"),
                @NamedAttributeNode("description"),
                @NamedAttributeNode("syntaxType")})
public class Commit {
   @Id
   @GeneratedValue(strategy = SEQUENCE, generator = "note_seq")
   @SequenceGenerator(name = "note_seq", sequenceName = "note_id_seq")
   Long id;

   @NotNull
   @Column(name = "note_id", nullable = false)
   Long note_id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "note_id", insertable = false, updatable = false)
   @Setter
   Entry entry;

   @NotNull
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
   @Column(name = "content", nullable = false)
   @Setter
   String content;

   @NotNull
   @Column(name = "syntax_type_id", nullable = false)
   @Convert(converter = SyntaxTypeConverter.class)
   @Setter
   SyntaxType syntaxType;

   @Column(name = "created_on", nullable = false)
   @Setter
   Instant createdOn;
}