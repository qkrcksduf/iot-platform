package io.wisoft.iotplatform.account.domain;

import io.wisoft.iotplatform.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {

  @Autowired
  private AccountRepository accountRepository;

  private Account account;

  @BeforeAll
  void setUp() {
    account = accountRepository.save(Account.builder()
        .username("tjqh55")
        .name("seobomin")
        .password("test123")
        .email("tjqh55@gmail.com")
        .role("ROLE_ADMIN")
        .description("석사과정").build());
  }

  @Test
  void Given_Account_When_AccountSaveOne_Then_ReturnValueWillOne() {

    assertNotNull(account);
    assertThat(account.getId()).isNotNull();
  }

  @Test
  void Given_Account_When_AccountFindByAll_Then_ReturnAccountList() {

    List<Account> accounts = accountRepository.findAll();
    assertThat(accounts).hasSize(3);
  }

  @Test
  void Given_Account_When_AccountUpdateOne_Then_WillUpdateAccount() {

    account.setName("서보민");
    account.setPassword("tjqhals");
    account.setEmail("tjqh55@naver.com");
    account.setUpdated(LocalDateTime.now());
    Account changeAccount = accountRepository.save(account);

    assertThat(changeAccount.getName()).isEqualTo("서보민");
    assertThat(changeAccount.getPassword()).isEqualTo("tjqhals");
    assertThat(changeAccount.getEmail()).isEqualTo("tjqh55@naver.com");
  }

  @Test
  void Given_Account_When_AccountDeleteOne_Then_ReturnNotEmpty() {

    accountRepository.delete(account);
    List<Account> accounts = accountRepository.findAll();
    assertThat(accounts).isNotEmpty();
  }


}