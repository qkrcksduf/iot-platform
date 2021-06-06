package io.wisoft.iotplatform.accountgroup.domain;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.repository.AccountRepository;
import io.wisoft.iotplatform.accountgroup.repository.AccountGroupRepository;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.repository.GroupRepository;
import io.wisoft.iotplatform.group.role.RoleWithinGroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountGroupTest {

  @Autowired
  private AccountGroupRepository accountGroupRepository;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private GroupRepository groupRepository;

  private AccountGroup accountGroup;

  private Account account;

  private Group group;

  @BeforeAll
  void setUp() {
    account = accountRepository.save(Account.builder()
        .username("tjqh55")
        .name("seobomin")
        .password("test123")
        .email("tjqh55@gmail.com")
        .role("ROLE_ADMIN")
        .description("석사과정").build());
    group = groupRepository.save(Group.builder()
        .id(UUID.randomUUID())
        .name("TestGroup")
        .joined(LocalDateTime.now())
        .updated(LocalDateTime.now())
        .description("Test하기 위한 Group입니다.").build());


    accountGroup = accountGroupRepository.save(AccountGroup.builder()
        .account(account)
        .group(group)
        .role(RoleWithinGroup.USER)
        .build());

  }


  @Test
  void Given_AccountGroup_When_AccountGroupSaveOne_Then_ReturnValueWillOne() {

    assertNotNull(accountGroup);
    assertThat(accountGroup.getId()).isNotNull();
  }

  @Test
  void Given_AccountGroup_When_AccoungGroupFindByAll_Then_ReturnAccountGroupList() {

    List<AccountGroup> accountGroups = accountGroupRepository.findAll();
    assertThat(accountGroups).hasSize(3);
  }

  @Test
  void Given_AccountGroup_When_AccountGroupUpdateOne_Then_WillUpdateAccount() {
    accountGroup.setRole(RoleWithinGroup.MANAGER);
    AccountGroup changeAccountGroup = accountGroupRepository.save(accountGroup);

    assertThat(changeAccountGroup.getRole()).isEqualTo(RoleWithinGroup.MANAGER);
  }

  @Test
  void Given_AccountGroup_When_AccountGroupDeleteOne_Then_ReturnNotEmpty() {
    accountGroupRepository.delete(accountGroup);

    List<AccountGroup> accountGroups = accountGroupRepository.findAll();
    assertThat(accountGroups).isNotEmpty();
  }

}