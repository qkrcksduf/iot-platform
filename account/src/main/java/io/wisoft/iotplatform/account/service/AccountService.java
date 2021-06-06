package io.wisoft.iotplatform.account.service;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.exception.AccountBadCredentialsExceptionException;
import io.wisoft.iotplatform.account.exception.AccountDuplicatedException;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.account.repository.AccountRepository;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountRegister;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountSignIn;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final ModelMapper modelMapper;

  public Account register(final AccountRegister accountDto) {
    checkDuplicateUsername(accountDto.getUsername());
    checkDuplicateEmail(accountDto.getEmail());

    final Account account = modelMapper.map(accountDto, Account.class);
    return accountRepository.save(account);
  }

  public Account update(final UUID id, final AccountUpdate updateDto) {
    final Account account = getAccount(id);
    account.setName(updateDto.getName());
    account.setPassword(updateDto.getPassword());
    account.setEmail(updateDto.getEmail());
    account.setUpdated(LocalDateTime.now());
    account.setDescription(updateDto.getDescription());

    return accountRepository.save(account);
  }

  public void remove(final UUID id) {
    accountRepository.delete(getAccount(id));
  }

  public Account signIn(final AccountSignIn singInDto) {
    final Account account = accountRepository.findByUsername(singInDto.getUsername()).orElseThrow(() -> new AccountNotFoundException(singInDto.getUsername()));

    if (account.getPassword().equals(singInDto.getPassword())) {
      return account;
    }

    throw new AccountBadCredentialsExceptionException(singInDto.getUsername());
  }

  public Account getAccount(final UUID id) {
    return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(String.valueOf(id)));
  }

  public Account getAccountByUsername(final String username) {
    return accountRepository.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(username));
  }

  public Page<Account> getAccounts(final Pageable pageable) {
    return accountRepository.findAll(pageable);
  }

  private void checkDuplicateUsername(final String username) {
    accountRepository.findByUsername(username).ifPresent(account -> {
      log.error("Account Duplicated Username Exception: {}", username);
      throw new AccountDuplicatedException(username);
    });
  }

  private void checkDuplicateEmail(final String email) {
    accountRepository.findByEmail(email).ifPresent(account -> {
      log.error("Account Duplicated Email Exception: {}", email);
      throw new AccountDuplicatedException(email);
    });
  }

}
