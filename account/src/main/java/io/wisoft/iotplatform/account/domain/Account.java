package io.wisoft.iotplatform.account.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.wisoft.iotplatform.accountgroup.domain.AccountGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
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
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

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
  @Column(name = "account_id")
  private UUID id;

  @NotNull
  @Size(min = 4, max = 15)
  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @NotNull
  @Size(max = 30)
  @Column(name = "account_name", nullable = false)
  private String name;

  @NotNull
  @Column(name = "password")
  private String password;

  @NotNull
  @Size(max = 50)
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @CreationTimestamp
  @Column(name = "join_date", nullable = false)
  private LocalDateTime joined;

  @CreationTimestamp
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime updated;

  @Size(max = 10)
  @Column(name = "account_role", nullable = false)
  @ColumnDefault("'ROLE_USER'")
  private String role;

  @Size(max = 250)
  @Column(name = "description")
  private String description;

  @JsonProperty(access = WRITE_ONLY)
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<AccountGroup> accountGroups;

}
