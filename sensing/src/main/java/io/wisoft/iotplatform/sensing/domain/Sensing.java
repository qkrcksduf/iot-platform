package io.wisoft.iotplatform.sensing.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@TypeDef(
    name = "jsonb",
    typeClass = JsonBinaryType.class
)
@Table(name = "sensing")
public class Sensing {

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO,
      generator = "pg-uuid"
  )
  @GenericGenerator(
      name = "pg-uuid",
      strategy = "uuid2",
      parameters = @Parameter(
          name = "uuid_gen_strategy_class",
          value = "io.wisoft.iotplatform.sensing.configurer.PostgreSQLUUIDGenerationStrategy"
      )
  )
  @Column(name = "sensing_id")
  private UUID id;

  private UUID sensorId;

  @CreationTimestamp
  @Column(name = "sensing_time", nullable = false)
  private LocalDateTime sensingTime;

  @Setter
  @Getter
  @Type(type = "jsonb")
  @Column(name = "sensing_value", nullable = false, columnDefinition = "jsonb")
  private HashMap<String, String> sensingValue;

}
