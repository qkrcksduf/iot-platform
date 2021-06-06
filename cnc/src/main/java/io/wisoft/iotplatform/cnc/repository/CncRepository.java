package io.wisoft.iotplatform.cnc.repository;

import io.wisoft.iotplatform.cnc.domain.Cnc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CncRepository extends JpaRepository<Cnc, UUID> {

  Optional<Cnc> findById(final UUID id);

  List<Cnc> findByGroupId(final UUID id);

  Optional<Cnc> findByName(final String name);

  Optional<Cnc> findBySerial(final String name);

  Optional<Cnc> findByIpAddress(final String ipAddress);

}
