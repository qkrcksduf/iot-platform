package io.wisoft.iotplatform.cnc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "cnc")
public class Cnc {

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
          value = "io.wisoft.iotplatform.cnc.configurer.PostgreSQLUUIDGenerationStrategy"
      )
  )
  @Column(name = "cnc_id")
  private UUID id;

  @NotNull
  @Size(max = 12)
  @Column(name = "cnc_serial", unique = true, nullable = false)
  private String serial;

  @NotNull
  @Size(min = 2, max = 30)
  @Column(name = "cnc_name", unique = true, nullable = false)
  private String name;

  @NotNull
  @Size(min = 5, max = 30)
  @Column(nullable = false)
  private String location;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "varchar(8) default 'INSTALL'", nullable = false)
  private CncStatus status;

  @NotNull
  @Size(max = 40)
  @Column(name = "ip_address", unique = true)
  private String ipAddress;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "join_date", nullable = false)
  private LocalDateTime joined;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime updated;

  @NotNull
  @Size(max = 250)
  @Column(name = "description")
  private String description;

  @JsonIgnore
  @Column(name = "group_id")
  private UUID groupId;

}
