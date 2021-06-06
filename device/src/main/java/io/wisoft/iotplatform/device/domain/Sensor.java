package io.wisoft.iotplatform.device.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "sensor")
public class Sensor {

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
  @Column(name = "sensor_id")
  private UUID id;

  @NotNull
  @Size(max = 12)
  @Column(name = "sensor_serial", unique = true, nullable = false)
  private String serial;

  @NotNull
  @Size(min = 2, max = 30)
  @Column(name = "sensor_name", unique = true, nullable = false)
  private String name;

  @CreationTimestamp
  @Column(name = "join_date", nullable = false)
  private LocalDateTime joined;

  @CreationTimestamp
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime updated;

  @Size(max = 250)
  @Column(name = "description")
  private String description;

  @Column(name = "model_id")
  private UUID modelId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "fk_sensor_device"))
  private Device device;

}
