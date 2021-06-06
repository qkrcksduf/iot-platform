package io.wisoft.iotplatform.device.repository;

import io.wisoft.iotplatform.device.domain.Device;
import io.wisoft.iotplatform.device.domain.Device.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

  Optional<Device> findById(final UUID id);

  Optional<Device> findByName(final String name);

  Optional<Device> findBySerial(final String serial);

  Optional<Device> findByIpAddress(final String ipAddress);

  List<Device> findByStatus(final DeviceStatus status);

  List<Device> findByCncId(final UUID cncId);

  @Query("SELECT d from Device d, Actuator a where d.id = a.device.id and a.id = :actuatorId")
  Optional<Device> findByActuatorId(@Param("actuatorId") UUID actuatorId);

}
