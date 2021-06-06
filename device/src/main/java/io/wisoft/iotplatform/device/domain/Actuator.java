package io.wisoft.iotplatform.device.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "actuator")
@Slf4j
public class Actuator {

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
          value = "io.wisoft.iotplatform.device.configurer.PostgreSQLUUIDGenerationStrategy"
      )
  )

  @Column(name = "actuator_id")
  private UUID id;

  @NotNull
  @Size(max = 12)
  @Column(name = "actuator_serial", unique = true)
  private String serial;

  @NotNull
  @Size(min = 2, max = 30)
  @Column(name = "actuator_name", unique = true)
  private String name;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "join_date")
  private LocalDateTime joined;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "modify_date")
  private LocalDateTime updated;

  @Size(max = 250)
  @Column(name = "description")
  private String description;

  @Column(name = "model_id")
  private UUID modelId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "fk_actuator_device"))
  private Device device;

//  @CommandHandler
//  public Actuator(final ActuatorValidationCheckCommand command) {
//    log.debug("handling {}", command);
//
//    if (command.getActuatorStatus().equals("impossible")) {
//      apply(ActuatorValidationCheckUnsuccessfulEvent.builder()
//              .actuatorId(command.getActuatorId())
//              .actuatorStatus(command.getActuatorStatus())
//              .actuatingValue(command.getActuatingValue())
//              .description(command.getDescription())
//              .build());
//
//    } else {
//      apply(ActuatorValidationCheckSuccessfulEvent.builder()
//              .actuatorId(command.getActuatorId())
//              .actuatorName(command.getActuatorName())
//              .actuatorStatus(command.getActuatorStatus())
//              .deviceId(command.getDeviceId())
//              .deviceName(command.getDeviceName())
//              .description(command.getDescription())
//              .actuatingValue(command.getActuatingValue())
//              .modelId(command.getModelId())
//              .build());
//    }
//  }

//  @CommandHandler
//  protected void checkExistActuator(ActuatorValidationCheckCommand command) {
//
//    log.debug("handling {}", command);
//
//    if (command.getActuatorStatus().equals("impossible")) {
//      apply(ActuatorValidationCheckUnsuccessfulEvent.builder()
//              .actuatorId(command.getActuatorId())
//              .actuatorStatus(command.getActuatorStatus())
//              .actuatingValue(command.getActuatingValue())
//              .description(command.getDescription())
//              .build());
//
//    } else {
//      apply(ActuatorValidationCheckSuccessfulEvent.builder()
//              .actuatorId(command.getActuatorId())
//              .actuatorName(command.getActuatorName())
//              .actuatorStatus(command.getActuatorStatus())
//              .deviceId(command.getDeviceId())
//              .deviceName(command.getDeviceName())
//              .description(command.getDescription())
//              .actuatingValue(command.getActuatingValue())
//              .modelId(command.getModelId())
//              .build());
//    }
//  }

}
