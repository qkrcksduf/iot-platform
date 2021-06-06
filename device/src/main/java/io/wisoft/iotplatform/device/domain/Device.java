package io.wisoft.iotplatform.device.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "device")
public class Device {

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
  @Column(name = "device_id")
  private UUID id;

  @NotNull
  @Size(max = 12)
  @Column(name = "device_serial", unique = true, nullable = false)
  private String serial;

  @NotNull
  @Size(min = 2, max = 30)
  @Column(name = "device_name", unique = true, nullable = false)
  private String name;

  @Column(name = "device_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private DeviceType deviceType;

  @NotNull
  @Size(min = 5, max = 30)
  @Column(nullable = false)
  private String location;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "varchar(8) default 'INSTALL'", nullable = false)
  private DeviceStatus status;

  @NotNull
  @ColumnDefault("100")
  private Integer battery;

  @NotNull
  @Size(max = 40)
  @Column(name = "ip_address", unique = true)
  private String ipAddress;

  @NotNull
  @Size(max = 4)
  @Column(name = "protocol", nullable = false)
  private String protocol;

  @NotNull
  @Column(name = "sleep", nullable = false)
  private boolean sleep;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "join_date", nullable = false)
  private LocalDateTime joined;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime updated;

  @Size(max = 250)
  @Column(name = "description")
  private String description;

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
  private List<Actuator> actuators = new ArrayList<>();

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
  private List<Sensor> sensors = new ArrayList<>();

  @Column(name = "cnc_id")
  private UUID cncId;

  public enum DeviceType {
    AN, SN, SAN
  }

  public enum DeviceStatus {
    ACTIVE, INACTIVE, INSTALL
  }

}
