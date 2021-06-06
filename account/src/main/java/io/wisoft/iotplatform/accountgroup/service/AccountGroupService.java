package io.wisoft.iotplatform.accountgroup.service;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.account.repository.AccountRepository;
import io.wisoft.iotplatform.accountgroup.domain.AccountGroup;
import io.wisoft.iotplatform.accountgroup.exception.AccountGroupNotFoundException;
import io.wisoft.iotplatform.accountgroup.repository.AccountGroupRepository;
import io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.AccountGroupRegister;
import io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.AccountGroupUpdate;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.exception.GroupNotFoundException;
import io.wisoft.iotplatform.group.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AccountGroupService {

  private AccountRepository accountRepository;
  private GroupRepository groupRepository;
  private AccountGroupRepository accountGroupRepository;
  private ModelMapper modelMapper;

  public AccountGroup register(final AccountGroupRegister dto) {
    final Account account = accountRepository.findById(dto.getAccountId()).orElseThrow(() -> new AccountNotFoundException(String.valueOf(dto.getAccountId())));
    final Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(() -> new GroupNotFoundException(String.valueOf(dto.getGroupId())));

    final AccountGroup accountGroup = modelMapper.map(dto, AccountGroup.class);
    accountGroup.setAccount(account);
    accountGroup.setGroup(group);
    accountGroup.setRole(dto.getRole());

    return accountGroupRepository.save(accountGroup);
  }

  public AccountGroup update(final UUID id, final AccountGroupUpdate updateDto) {
    final AccountGroup accountGroup = getAccountGroup(id);
    accountGroup.setRole(updateDto.getRole());

    return accountGroupRepository.save(accountGroup);
  }

  public void remove(final UUID id) {
    accountGroupRepository.delete(getAccountGroup(id));
  }

  public AccountGroup getAccountGroup(final UUID id) {
    return accountGroupRepository.findById(id).orElseThrow(() -> new AccountGroupNotFoundException(String.valueOf(id)));
  }

  public List<AccountGroup> getAccountGroupByAccountId(final UUID id) {
    final List<AccountGroup> accountGroups = accountGroupRepository.findByAccountId(id);
    if (accountGroups == null || accountGroups.isEmpty()) {
      log.error("AccountGroupService-getAccountGroup: Account Group Not Found Exception: {}", id);
      throw new AccountGroupNotFoundException(String.valueOf(id));
    }

    return accountGroups;
  }

  public Page<AccountGroup> getAccountGroups(final Pageable pageable) {
    return accountGroupRepository.findAll(pageable);
  }

}