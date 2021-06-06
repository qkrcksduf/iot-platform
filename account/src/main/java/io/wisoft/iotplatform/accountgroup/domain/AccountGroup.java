package io.wisoft.iotplatform.accountgroup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.role.RoleWithinGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_group")
public class AccountGroup {

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
  @Column(name = "account_group_id")
  private UUID id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_accountGroup_account"),
      nullable = false)
  private Account account;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_accountGroup_group"),
      nullable = false)
  private Group group;

  @Column(name = "group_role")
  @Enumerated(EnumType.STRING)
  private RoleWithinGroup role;

}
