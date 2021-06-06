package io.wisoft.iotplatform.account.service;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.exception.AccountBadCredentialsExceptionException;
import io.wisoft.iotplatform.account.exception.AccountDuplicatedException;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.account.repository.MemoryAccountRepository;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountRegister;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountSignIn;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

  private AccountService accountService;
  private MemoryAccountRepository memoryAccountRepository;
  private ModelMapper modelMapper;

  @BeforeEach
  public void setup() {
    this.memoryAccountRepository = new MemoryAccountRepository();
    this.modelMapper = new ModelMapper();
    this.accountService = new AccountService(memoryAccountRepository, modelMapper);
  }

  @Nested
  @DisplayName("사용자 등록 기능 테스트")
  class RegisterAccountTest {

    private AccountRegister accountRegister;

    @BeforeEach
    void setup() {
      accountRegister = AccountRegister.builder()
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();
    }

    @Test
    @DisplayName("사용자 등록시 성공 사례 테스트")
    void givenNewAccount_whenRegister_thenRegisterNewAccount() {
      final Account registeredAccount = accountService.register(accountRegister);

      assertEquals(accountRegister.getUsername(), registeredAccount.getUsername());
      assertEquals(accountRegister.getName(), registeredAccount.getName());
      assertEquals(accountRegister.getPassword(), registeredAccount.getPassword());
      assertEquals(accountRegister.getEmail(), registeredAccount.getEmail());
      assertEquals(accountRegister.getRole(), registeredAccount.getRole());
      assertEquals(accountRegister.getDescription(), registeredAccount.getDescription());
    }

    @Test
    @DisplayName("새로운 사용자의 유저네임 중복된 경우 익셉션 발생")
    void givenDuplicatedUsername_whenRegister_thenThrowException() {
      final Account alreadyRegisteredAccount = Account.builder().username("gildong").build();
      memoryAccountRepository.save(alreadyRegisteredAccount);

      assertThrows(AccountDuplicatedException.class, () -> accountService.register(accountRegister));
    }

    @Test
    @DisplayName("새로운 사용자의 이메일이 중복된 경우 익셉션 발생")
    void givenDuplicatedEmail_whenRegister_thenThrowException() {
      final Account alreadyRegisteredAccount = Account.builder()
          .username("gildong")
          .email("gildong@wisoft.io")
          .build();
      memoryAccountRepository.save(alreadyRegisteredAccount);

      assertThrows(AccountDuplicatedException.class, () -> accountService.register(accountRegister));
    }

  }

  @Nested
  @DisplayName("사용자 정보 수정 기능 테스트")
  class UpdateAccountTest {

    private Account registeredAccount;

    @BeforeEach
    void setup() {
      final AccountRegister accountRegister = AccountRegister.builder()
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();

      registeredAccount = accountService.register(accountRegister);
    }

    @Test
    @DisplayName("사용자 정보 수정시 성공 사례 테스트")
    void givenUpdateInfo_whenUpdate_thenReturnUpdatedAccount() {
      final UUID accountId = registeredAccount.getId();
      final AccountUpdate accountUpdate = AccountUpdate.builder()
          .name("이몽룡")
          .password("4321")
          .email("monglyong@wisoft.io")
          .description("UPDATED DESCRIPTION")
          .build();

      final Account updatedAccount = accountService.update(accountId, accountUpdate);

      assertEquals(registeredAccount.getName(), updatedAccount.getName());
      assertEquals(registeredAccount.getPassword(), updatedAccount.getPassword());
      assertEquals(registeredAccount.getEmail(), updatedAccount.getEmail());
      assertEquals(registeredAccount.getDescription(), updatedAccount.getDescription());
    }

    @Test
    @DisplayName("정보 수정을 요청한 식별자에 해당하는 사용자가 없을 경우 익셉션 발생")
    void givenNotExistId_whenUpdate_thenThrowException() {
      final UUID notExistAccountId = UUID.randomUUID();
      final AccountUpdate accountUpdate = AccountUpdate
          .builder()
          .name("이몽룡")
          .password("4321")
          .email("monglyong")
          .description("UPDATED DESCRIPTION")
          .build();

      assertThrows(AccountNotFoundException.class, () -> accountService.update(notExistAccountId, accountUpdate));
    }

  }

  @Nested
  @DisplayName("사용자 정보 삭제 기능 테스트")
  class RemoveAccountTest {

    private AccountRegister accountRegister;
    private Account registeredAccount;

    @BeforeEach
    void setup() {
      accountRegister = AccountRegister.builder()
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();

      registeredAccount = accountService.register(accountRegister);
    }

    @Test
    @DisplayName("회원 정보 삭제시 성공 사례 테스트")
    void givenAccountId_whenRemove_thenRemoveAccount() {
      final UUID accountId = registeredAccount.getId();

      accountService.remove(accountId);

      assertTrue(memoryAccountRepository.findById(accountId).isEmpty());
    }

  }

  @Nested
  @DisplayName("로그인 기능 테스트")
  class SignInTest {

    private AccountRegister accountRegister;
    private Account registeredAccount;

    @BeforeEach
    void setup() {
      accountRegister = AccountRegister.builder()
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();

      registeredAccount = accountService.register(accountRegister);
    }

    @Test
    @DisplayName("로그인시 성공 사례 테스트")
    void givenAccountSignIn_whenSignIn_thenReturnAccountResponse() {
      final AccountSignIn accountSignIn = new AccountSignIn("gildong", "1234");

      final Account signedAccount = accountService.signIn(accountSignIn);

      assertEquals(registeredAccount, signedAccount);
    }

    @Test
    @DisplayName("로그인시 로그인 정보가 유효하지 않은 경우 - 유저네임 불일치")
    void givenNotExistUsername_whenSignIn_thenThrowException() {
      final AccountSignIn accountSignIn = new AccountSignIn("gil", "1234");

      assertThrows(AccountNotFoundException.class, () -> accountService.signIn(accountSignIn));
    }

    @Test
    @DisplayName("로그인시 로그인 정보가 유효하지 않은 경우 - 비밀번호 불일")
    void givenNotValidPassword_whenSignIn_thenThrowException() {
      final AccountSignIn accountSignIn = new AccountSignIn("gildong", "123");

      assertThrows(AccountBadCredentialsExceptionException.class, () -> accountService.signIn(accountSignIn));
    }

  }

  @Nested
  @DisplayName("사용자 조회 기능 테스트")
  class GetAccountsTest {

    private AccountRegister accountRegister;
    private Account registeredAccount;

    @BeforeEach
    void setup() {
      accountRegister = AccountRegister.builder()
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();

      registeredAccount = accountService.register(accountRegister);
    }

    @Test
    @DisplayName("사용자 조회시 성공 사례 테스트")
    void givenAccountId_whenGetAccount_thenReturnAccount() {
      final UUID accountId = registeredAccount.getId();

      final Account foundAccount = accountService.getAccount(accountId);

      assertEquals(registeredAccount, foundAccount);
    }

    @Test
    @DisplayName("사용자 조회시 식별자에 해당하는 회원이 없을 경우 익셉션 발생")
    void givenNotExistAccountId_whenGetAccount_thenThrowException() {
      final UUID notExistAccountId = UUID.randomUUID();

      assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(notExistAccountId));
    }

  }

  @Nested
  @DisplayName("유저네임에 해당하는 사용자 조회 기능 테스트")
  class GetAccountByUsername {

    private AccountRegister accountRegister;
    private Account registeredAccount;

    @BeforeEach
    void setup() {
      accountRegister = AccountRegister.builder()
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();

      registeredAccount = accountService.register(accountRegister);
    }

    @Test
    @DisplayName("사용자 조회시 성공 사례 테스트")
    void givenUsername_whenGetAccountByUsername_thenReturnAccount() {
      final String username = registeredAccount.getUsername();

      final Account foundAccount = accountService.getAccountByUsername(username);

      assertEquals(registeredAccount, foundAccount);
    }

    @Test
    @DisplayName("사용자 조회시 유저네임에 해당하는 사용자가 없을 경우 익셉션 발생")
    void givenNotExistUsername_whenGetAccountByUsername_thenThrowException() {
      final String notExistUsername = "gil";

      assertThrows(AccountNotFoundException.class, () -> accountService.getAccountByUsername(notExistUsername));
    }

  }

}