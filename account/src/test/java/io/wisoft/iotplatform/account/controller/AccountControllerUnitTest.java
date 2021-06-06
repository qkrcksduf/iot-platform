package io.wisoft.iotplatform.account.controller;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.exception.AccountBadCredentialsExceptionException;
import io.wisoft.iotplatform.account.exception.AccountDuplicatedException;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.account.service.AccountService;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountRegister;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountResponse;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountSignIn;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AccountControllerUnitTest {

  private AccountController accountController;
  private AccountService accountService;
  private ModelMapper modelMapper;

  @BeforeEach
  public void setup() {
    this.accountService = mock(AccountService.class);
    this.modelMapper = new ModelMapper();
    this.accountController = new AccountController(accountService, modelMapper);
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
      final Account account = Account.builder()
          .id(UUID.randomUUID())
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .joined(LocalDateTime.now())
          .updated(LocalDateTime.now())
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();
      given(accountService.register(accountRegister)).willReturn(account);

      final AccountResponse accountresponse = accountController.register(accountRegister);

      assertEquals(account.getId(), accountresponse.getId());
      assertEquals(account.getUsername(), accountresponse.getUsername());
      assertEquals(account.getName(), accountresponse.getName());
      assertEquals(account.getEmail(), accountresponse.getEmail());
      assertEquals(account.getJoined(), accountresponse.getJoined());
      assertEquals(account.getUpdated(), accountresponse.getUpdated());
      assertEquals(account.getRole(), accountresponse.getRole());
      assertEquals(account.getDescription(), accountresponse.getDescription());
    }

    @Test
    @DisplayName("새로운 사용자의 유저네임 중복된 경우 익셉션 발생")
    void givenDuplicatedUsername_whenRegister_thenThrowException() {
      final AccountRegister accountRegisterWithDuplicatedUsername = accountRegister;
      given(accountService.register(accountRegisterWithDuplicatedUsername)).willThrow(AccountDuplicatedException.class);

      assertThrows(AccountDuplicatedException.class, () -> accountController.register(accountRegister));
    }

    @Test
    @DisplayName("새로운 사용자의 이메일이 중복된 경우 익셉션 발생")
    void givenDuplicatedEmail_whenRegister_thenThrowException() {
      final AccountRegister accountRegisterWithDuplicatedEmail = accountRegister;
      given(accountService.register(accountRegisterWithDuplicatedEmail)).willThrow(AccountDuplicatedException.class);

      assertThrows(AccountDuplicatedException.class, () -> accountController.register(accountRegister));
    }

  }

  @Nested
  @DisplayName("사용자 정보 수정 기능 테스트")
  class UpdateAccountTest {

    private UUID accountId;
    private AccountUpdate updateDto;
    private AccountResponse responseDto;

    @BeforeEach
    void setup() {
      this.accountId = UUID.randomUUID();
      this.updateDto = AccountUpdate.builder()
          .name("이몽룡")
          .password("4321")
          .email("monglyong@wisoft.io")
          .description("UPDATED DESCRIPTION")
          .build();
    }

    @Test
    @DisplayName("사용자 정보 수정시 성공 사례 테스트")
    void givenUpdateInfo_whenUpdate_thenReturnUpdatedAccount() {
      final Account updatedAccount = Account.builder()
          .id(UUID.randomUUID())
          .username("gildong")
          .name("이몽룡")
          .password("4321")
          .email("monglyong@wisoft.io")
          .joined(LocalDateTime.now())
          .updated(LocalDateTime.now())
          .role("ROLE_ADMIN")
          .description("UPDATED DESCRIPTION")
          .build();
      given(accountService.update(accountId, updateDto)).willReturn(updatedAccount);

      responseDto = accountController.update(accountId, updateDto);

      assertEquals(updateDto.getName(), responseDto.getName());
      assertEquals(updateDto.getEmail(), responseDto.getEmail());
      assertEquals(updateDto.getDescription(), responseDto.getDescription());
    }

    @Test
    @DisplayName("정보 수정을 요청한 식별자에 해당하는 사용자가 없을 경우 익셉션 발생")
    void givenNotExistId_whenUpdate_thenThrowException() {
      final UUID notExistAccountId = accountId;
      given(accountService.update(notExistAccountId, updateDto)).willThrow(AccountNotFoundException.class);

      assertThrows(AccountNotFoundException.class, () -> accountController.update(notExistAccountId, updateDto));
    }

  }

  @Nested
  @DisplayName("로그인 기능 테스트")
  class SignInTest {

    private AccountSignIn signInDto;
    private AccountResponse responseDto;

    @BeforeEach
    void setup() {
      signInDto = AccountSignIn.builder()
          .username("gildong")
          .password("1234")
          .build();
    }

    @Test
    @DisplayName("로그인시 성공 사례 테스트")
    void givenAccountSignIn_whenSignIn_thenReturnAccountResponse() {
      final Account account = Account.builder()
          .id(UUID.randomUUID())
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .joined(LocalDateTime.now())
          .updated(LocalDateTime.now())
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();
      given(accountService.signIn(signInDto)).willReturn(account);

      responseDto = accountController.signIn(signInDto);

      assertEquals(account.getId(), responseDto.getId());
      assertEquals(account.getUsername(), responseDto.getUsername());
      assertEquals(account.getName(), responseDto.getName());
      assertEquals(account.getEmail(), responseDto.getEmail());
      assertEquals(account.getJoined(), responseDto.getJoined());
      assertEquals(account.getUpdated(), responseDto.getUpdated());
      assertEquals(account.getRole(), responseDto.getRole());
      assertEquals(account.getDescription(), responseDto.getDescription());
    }

    @Test
    @DisplayName("로그인시 로그인 정보가 유효하지 않은 경우 - 유저네임 불일치")
    void givenNotExistUsername_whenSignIn_thenThrowException() {
      given(accountService.signIn(signInDto)).willThrow(AccountNotFoundException.class);

      assertThrows(AccountNotFoundException.class, () -> accountController.signIn(signInDto));
    }

    @Test
    @DisplayName("로그인시 로그인 정보가 유효하지 않은 경우 - 비밀번호 불일")
    void givenNotValidPassword_whenSignIn_thenThrowException() {
      given(accountService.signIn(signInDto)).willThrow(AccountBadCredentialsExceptionException.class);

      assertThrows(AccountBadCredentialsExceptionException.class, () -> accountController.signIn(signInDto));
    }

  }

  @Nested
  @DisplayName("특정 사용자 조회 기능 테스트")
  class GetAccountByIdTest {

    private UUID accountId;

    @BeforeEach
    void setup() {
      this.accountId = UUID.randomUUID();
    }

    @Test
    @DisplayName("사용자 조회시 성공 사례 테스트")
    void givenAccountId_whenGetAccount_thenReturnAccount() {
      final Account account = Account.builder()
          .id(UUID.randomUUID())
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .joined(LocalDateTime.now())
          .updated(LocalDateTime.now())
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();
      given(accountService.getAccount(accountId)).willReturn(account);

      AccountResponse responseDto = accountController.getAccount(accountId);

      assertEquals(account.getId(), responseDto.getId());
      assertEquals(account.getUsername(), responseDto.getUsername());
      assertEquals(account.getName(), responseDto.getName());
      assertEquals(account.getEmail(), responseDto.getEmail());
      assertEquals(account.getJoined(), responseDto.getJoined());
      assertEquals(account.getUpdated(), responseDto.getUpdated());
      assertEquals(account.getRole(), responseDto.getRole());
      assertEquals(account.getDescription(), responseDto.getDescription());
    }

    @Test
    @DisplayName("사용자 조회시 식별자에 해당하는 회원이 없을 경우 익셉션 발생")
    void givenNotExistAccountId_whenGetAccount_thenThrowException() {
      final UUID notExistAccountId = accountId;
      given(accountService.getAccount(notExistAccountId)).willThrow(AccountNotFoundException.class);

      assertThrows(AccountNotFoundException.class, () -> accountController.getAccount(notExistAccountId));
    }

  }

  @Nested
  @DisplayName("유저네임에 해당하는 사용자 조회 기능 테스트")
  class GetAccountByUsername {

    private String username;

    @BeforeEach
    void setup() {
      this.username = new String("gildong");
    }


    @Test
    @DisplayName("사용자 조회시 성공 사례 테스트")
    void givenUsername_whenGetAccountByUsername_thenReturnAccount() {
      final Account account = Account.builder()
          .id(UUID.randomUUID())
          .username("gildong")
          .name("홍길동")
          .password("1234")
          .email("gildong@wisoft.io")
          .joined(LocalDateTime.now())
          .updated(LocalDateTime.now())
          .role("ROLE_ADMIN")
          .description("DESCRIPTION")
          .build();

      given(accountService.getAccountByUsername(username)).willReturn(account);

      final AccountResponse responseDto = accountController.getAccountByUsername(username);

      assertEquals(account.getId(), responseDto.getId());
      assertEquals(account.getUsername(), responseDto.getUsername());
      assertEquals(account.getName(), responseDto.getName());
      assertEquals(account.getEmail(), responseDto.getEmail());
      assertEquals(account.getJoined(), responseDto.getJoined());
      assertEquals(account.getUpdated(), responseDto.getUpdated());
      assertEquals(account.getRole(), responseDto.getRole());
      assertEquals(account.getDescription(), responseDto.getDescription());
    }

    @Test
    @DisplayName("사용자 조회시 유저네임에 해당하는 사용자가 없을 경우 익셉션 발생")
    void givenNotExistUsername_whenGetAccountByUsername_thenThrowException() {
      final String notExistUsername = username;

      given(accountService.getAccountByUsername(username)).willThrow(AccountNotFoundException.class);

      assertThrows(AccountNotFoundException.class, () -> accountController.getAccountByUsername(notExistUsername));
    }

  }

}
