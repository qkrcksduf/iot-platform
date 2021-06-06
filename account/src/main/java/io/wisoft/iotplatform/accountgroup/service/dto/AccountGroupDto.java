package io.wisoft.iotplatform.accountgroup.service.dto;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.role.RoleWithinGroup;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountGroupDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AccountGroupRegister {

    @NotNull
    private UUID accountId;

    @NotNull
    private UUID groupId;
    private RoleWithinGroup role;

  }

  @Getter
  @Setter
  public static class AccountGroupUpdate {

    @NotNull
    private RoleWithinGroup role;

  }

  @Getter
  @Setter
  public static class AccountGroupResponse {

    private UUID id;
    private Account account;
    private Group group;
    private RoleWithinGroup role;

  }

}
