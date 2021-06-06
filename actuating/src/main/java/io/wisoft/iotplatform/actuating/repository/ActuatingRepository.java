package io.wisoft.iotplatform.actuating.repository;

import io.wisoft.iotplatform.actuating.domain.Actuating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActuatingRepository extends JpaRepository<Actuating, UUID> {

  Optional<Actuating> findById(final UUID id);

}
