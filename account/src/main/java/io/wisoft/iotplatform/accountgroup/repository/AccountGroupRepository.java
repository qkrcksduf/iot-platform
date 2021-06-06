package io.wisoft.iotplatform.accountgroup.repository;

import io.wisoft.iotplatform.accountgroup.domain.AccountGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountGroupRepository extends JpaRepository<AccountGroup, UUID> {

  Optional<AccountGroup> findById(final UUID id);

  List<AccountGroup> findByAccountId(final UUID id);

}
