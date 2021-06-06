package io.wisoft.iotplatform.group.repository;

import io.wisoft.iotplatform.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

  Optional<Group> findById(final UUID id);

  Optional<Group> findByName(final String name);

}
