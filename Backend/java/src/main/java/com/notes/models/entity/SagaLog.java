package com.notes.models.entity;

import com.notes.converters.SyntaxTypeConverter;
import com.notes.listeners.NoteListener;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "saga_logs")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class SagaLog {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotNull
   @Column(name = "saga_uuid", nullable = false)
   private UUID sagaUuid;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "note_id", nullable = false)
   private Note note;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "commit_id", nullable = false)
   private Commit commit;

   @NotNull
   @Column(name = "payload", nullable = false)
   @JdbcTypeCode(SqlTypes.JSON)
   private Map<String, Object> payload;

   @NotNull
   @Column(name = "created_on", nullable = false)
   private Instant createdOn;

   @NotNull
   @Column(name = "event_id", nullable = false)
   @Convert(converter = SyntaxTypeConverter.class)
   @Setter
   SagaEvent event;
}