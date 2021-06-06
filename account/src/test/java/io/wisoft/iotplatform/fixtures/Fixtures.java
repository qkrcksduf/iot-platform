package io.wisoft.iotplatform.fixtures;

import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountRegister;
import io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.AccountGroupRegister;
import io.wisoft.iotplatform.group.role.RoleWithinGroup;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupRegister;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fixtures {
  public static AccountRegister registerAccountDto() {
    return AccountRegister.builder()
        .username("testUser")
        .name("testUser")
        .password("password")
        .email("testuser@hanbat.ac.kr")
        .role("RULE_USER")
        .description("테스트 유저의 설명")
        .build();
  }

  public static AccountGroupRegister registerAccountGroupDto() {
    final AccountGroupRegister registerDto =
        AccountGroupRegister.builder().role(RoleWithinGroup.USER).build();
    return registerDto;
  }

  public static GroupRegister registerGroupDto() {
    return GroupRegister.builder().name("wisoft_test").description("테스트 그룹입니다.").build();
  }

}
