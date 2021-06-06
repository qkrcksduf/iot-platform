package io.wisoft.iotplatform.actuating.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "actuating")
public class Actuating {

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO,
      generator = "pg-uuid"
  )
  @GenericGenerator(
      name = "pg-uuid",
      strategy = "uuid2",
      parameters = @org.hibernate.annotations.Parameter(
          name = "uuid_gen_strategy_class",
          value = "io.wisoft.iotplatform.actuating.configurer.PostgreSQLUUIDGenerationStrategy"
      )
  )
  @Column(name = "actuating_id")
  private UUID id;

  private UUID actuatorId;

  @CreationTimestamp
  @Column(name = "actuating_time", nullable = false)
  private LocalDateTime actuatingTime;

  @Column(name = "actuating_value", nullable = false)
  private String actuatingValue;

  @Enumerated(EnumType.STRING)
  @Column(name = "result", columnDefinition = "varchar(7) default 'RUNNING'", nullable = false)
  private Status result;

  public enum Status {
    SUCCESS, FAIL, RUNNING
  }

}
