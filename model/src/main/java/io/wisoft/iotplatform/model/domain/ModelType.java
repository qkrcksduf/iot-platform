package io.wisoft.iotplatform.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.wisoft.iotplatform.model.domain.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "model_type")
public class ModelType {

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
          value = "io.wisoft.iotplatform.model.configurer.PostgreSQLUUIDGenerationStrategy"
      )
  )
  @Column(name = "model_type_id")
  private UUID id;

  @NotNull
  @Size(min = 2, max = 30)
  @Column(name = "model_type_name", unique = true, nullable = false)
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

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "modelType", fetch = FetchType.EAGER)
  private List<Model> models = new ArrayList<>();

}
