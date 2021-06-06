package io.wisoft.iotplatform.sensing.repository;

import io.wisoft.iotplatform.sensing.domain.Sensing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SensingRepository extends JpaRepository<Sensing, UUID> {

  Optional<Sensing> findById(final UUID id);

  List<Sensing> findBySensorId(final UUID id);

}
