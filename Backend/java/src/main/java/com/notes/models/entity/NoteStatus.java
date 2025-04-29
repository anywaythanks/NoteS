package com.notes.models.entity;

import com.notes.converters.StateConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

/**
 * Существует только в рамках слоя entity, поскольку не имеет особого смысла выше и является деталью реализации.
 */
@Entity
@Table(name = "note_status")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class NoteStatus {
   @Id
   @GeneratedValue(strategy = SEQUENCE, generator = "account_seq")
   @SequenceGenerator(name = "account_seq", sequenceName = "account_id_seq")
   Long id;

   @NotNull
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "note_id", nullable = false, updatable = false, insertable = false)
   Note note;

   @NotNull
   @Column(name = "state", nullable = false)
   @Convert(converter = StateConverter.class)
   @Setter
   State state;

   @NotNull
   @Column(name = "state_snapshot")
   private String stateSnapshot;

   @NotNull
   @Column(name = "state_version")
   private Integer stateVersion;
}