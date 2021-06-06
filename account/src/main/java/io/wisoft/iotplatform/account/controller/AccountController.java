package io.wisoft.iotplatform.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.account.service.AccountService;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountRegister;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountResponse;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountSignIn;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountUpdate;
import io.wisoft.iotplatform.common.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/accounts")
@Api(value = "accounts")
@Slf4j
@AllArgsConstructor
public class AccountController {

  private AccountService accountService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "사용자 등록", notes = "사용자를 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public AccountResponse register(@RequestBody @Valid final AccountRegister registerDto) {
    final Account newAccount = accountService.register(registerDto);

    return modelMapper.map(newAccount, AccountResponse.class);
  }

  @ApiOperation(value = "사용자 정보 갱신", notes = "시스템에 등록되어 있는 사용자의 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public AccountResponse update(@PathVariable final UUID id, @RequestBody @Valid final AccountUpdate updateDto) {
    final Account updatedAccount = accountService.update(id, updateDto);

    return modelMapper.map(updatedAccount, AccountResponse.class);
  }

  @ApiOperation(value = "사용자 정보 삭제", notes = "시스템에서 특정 사용자의 정보를 삭제합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
  })
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    accountService.remove(id);
  }

  @ApiOperation(value = "사용자 정보 조회(로그인)", notes = "사용자의 정보를 조회(로그인)합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Invalid Account"),
      @ApiResponse(code = 404, message = "Account Not Found", response = AccountNotFoundException.class),
      @ApiResponse(code = 405, message = "Invalid Information", response = ErrorResponse.class)
  })
  @ResponseStatus(OK)
  @PutMapping(value = "/signin")
  public AccountResponse signIn(@ApiParam(value = "Account SignIn Information", required = true)
                                @RequestBody @Valid final AccountSignIn dto) {

    return this.modelMapper.map(accountService.signIn(dto), AccountResponse.class);
  }

  @ApiOperation(value = "특정 사용자 조회", notes = "시스템에 등록되어 있는 특정 사용자를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public AccountResponse getAccount(@PathVariable final UUID id) {
    final Account account = accountService.getAccount(id);

    return modelMapper.map(account, AccountResponse.class);
  }

  @ApiOperation(value = "전체 사용자 조회", notes = "시스템에 등록되어 있는 전체 사용자를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<AccountResponse> getAccounts(final Pageable pageable) {
    final Page<Account> accounts = accountService.getAccounts(pageable);
    final List<AccountResponse> content = accounts
        .getContent()
        .stream()
        .map(account -> modelMapper.map(account, AccountResponse.class))
        .collect(toList());

    return new PageImpl<>(content, pageable, accounts.getTotalElements());
  }

  @ApiOperation(value = "특정 사용자 조회: Username", notes = "시스템에 등록되어 있는 특정 사용자를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/username")
  public AccountResponse getAccountByUsername(@RequestParam(value = "q") final String username) {
    final Account account = accountService.getAccountByUsername(username);

    return modelMapper.map(account, AccountResponse.class);
  }

}

