package io.wisoft.iotplatform.account.repository;

import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.repository.AccountRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class MemoryAccountRepository implements AccountRepository {

  private List<Account> accounts = new ArrayList<>();

  @Override
  public Optional<Account> findById(final UUID accountId) {
    return accounts
        .stream()
        .filter(account -> account.getId().equals(accountId))
        .findFirst();
  }

  @Override
  public boolean existsById(final UUID uuid) {
    return false;
  }

  @Override
  public Optional<Account> findByEmail(final String email) {
    return accounts
        .stream()
        .filter(account -> account.getEmail().equals(email))
        .findFirst();
  }

  @Override
  public Optional<Account> findByUsername(final String username) {
    return accounts
        .stream()
        .filter(account -> account.getUsername().equals(username))
        .findFirst();
  }

  @Override
  public List<Account> findAll() {
    return null;
  }

  @Override
  public List<Account> findAll(final Sort sort) {
    return null;
  }

  @Override
  public Page<Account> findAll(final Pageable pageable) {
    return null;
  }

  @Override
  public List<Account> findAllById(final Iterable<UUID> uuids) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(final UUID uuid) {

  }

  @Override
  public void delete(final Account entity) {
    this.accounts.remove(entity);
  }

  @Override
  public void deleteAll(final Iterable<? extends Account> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Account> S save(final S entity) {
    entity.setId(UUID.randomUUID());
    this.accounts.add(entity);

    return entity;
  }

  @Override
  public <S extends Account> List<S> saveAll(final Iterable<S> entities) {
    return null;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Account> S saveAndFlush(final S entity) {
    return null;
  }

  @Override
  public void deleteInBatch(final Iterable<Account> entities) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Account getOne(final UUID uuid) {
    return null;
  }

  @Override
  public <S extends Account> Optional<S> findOne(final Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Account> List<S> findAll(final Example<S> example) {
    return null;
  }

  @Override
  public <S extends Account> List<S> findAll(final Example<S> example, final Sort sort) {
    return null;
  }

  @Override
  public <S extends Account> Page<S> findAll(final Example<S> example, final Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Account> long count(final Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Account> boolean exists(final Example<S> example) {
    return false;
  }

}
