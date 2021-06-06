package io.wisoft.iotplatform.accountgroup.service;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.service.AccountService;
import io.wisoft.iotplatform.accountgroup.domain.AccountGroup;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.service.GroupService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.iotplatform.account.service.dto.AccountDto.*;
import static io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.*;
import static io.wisoft.iotplatform.fixtures.Fixtures.registerAccountDto;
import static io.wisoft.iotplatform.fixtures.Fixtures.registerAccountGroupDto;
import static io.wisoft.iotplatform.fixtures.Fixtures.registerGroupDto;
import static io.wisoft.iotplatform.group.service.dto.GroupDto.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountGroupServiceTest {

  @Autowired
  private AccountGroupService accountGroupService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private GroupService groupService;

  private AccountGroupRegister registerAccountGroupDto;
  private AccountRegister registerAccountDto;
  private GroupRegister registerGroupDto;

  @BeforeAll
  public void setup() {
    this.registerAccountDto = registerAccountDto();
    this.registerGroupDto = registerGroupDto();
    this.registerAccountGroupDto = registerAccountGroupDto();
  }

  @Test
  public void equals() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);
    final Group group = groupService.register(this.registerGroupDto);

    this.registerAccountGroupDto.setAccountId(account.getId());
    this.registerAccountGroupDto.setGroupId(group.getId());
    final AccountGroup accountGroup =
        accountGroupService.register(this.registerAccountGroupDto);

    // When & Then
    assertTrue(accountGroup.equals(accountGroup), "Class equals to itself.");
  }

  @Test
  public void isHashcodeConsistent() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);
    final Group group = groupService.register(this.registerGroupDto);

    this.registerAccountGroupDto.setAccountId(account.getId());
    this.registerAccountGroupDto.setGroupId(group.getId());
    final AccountGroup accountGroup =
        accountGroupService.register(this.registerAccountGroupDto);

    // When & Then
    assertEquals(accountGroup.hashCode(), accountGroup.hashCode());
  }

}