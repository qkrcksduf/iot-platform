package io.wisoft.iotplatform.device.repository;

import io.wisoft.iotplatform.device.domain.Actuator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActuatorRepository extends JpaRepository<Actuator, UUID> {

  Optional<Actuator> findById(final UUID id);

  Optional<Actuator> findByName(final String name);

  Optional<Actuator> findBySerial(final String serial);

  @Query("select a from Device d, Actuator a where d.id = a.device.id and d.id = :id")
  List<Actuator> getByDeviceId(@Param("id") final UUID deviceId);

}
