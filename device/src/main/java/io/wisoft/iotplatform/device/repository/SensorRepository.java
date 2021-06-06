package io.wisoft.iotplatform.device.repository;

import io.wisoft.iotplatform.device.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {

  Optional<Sensor> findById(final UUID id);

  Optional<Sensor> findByName(final String name);

  Optional<Sensor> findBySerial(final String serial);

  List<Sensor> findByDeviceId(final UUID deviceId);

}
