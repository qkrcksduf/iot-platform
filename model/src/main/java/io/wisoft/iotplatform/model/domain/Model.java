package io.wisoft.iotplatform.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@EqualsAndHashCode(exclude = "modelType")
@Table(name = "model")
public class Model {

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
  @Column(name = "model_id")
  private UUID id;

  @NotNull
  @Size(min = 4, max = 30)
  @Column(name = "model_name", unique = true, nullable = false)
  private String name;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "model_type_id", foreignKey = @ForeignKey(name = "fk_model_modelType"),
      nullable = false)
  private ModelType modelType;

  @CreationTimestamp
  @Column(name = "join_date", nullable = false)
  private LocalDateTime joined;

  @CreationTimestamp
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime updated;

  @Size(max = 250)
  @Column(name = "description")
  private String description;

  public void setModelType(final ModelType modelType) {
    this.modelType = modelType;
    if (!modelType.getModels().contains(this)) {
      modelType.getModels().add(this);
    }
  }

}
