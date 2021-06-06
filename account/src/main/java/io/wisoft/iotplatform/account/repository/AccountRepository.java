package io.wisoft.iotplatform.account.repository;

import io.wisoft.iotplatform.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  Optional<Account> findById(final UUID accountId);

  Optional<Account> findByEmail(final String email);

  Optional<Account> findByUsername(final String username);

}
