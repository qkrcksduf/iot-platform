package io.wisoft.iotplatform.device.service;

import io.wisoft.iotplatform.device.domain.Actuator;
import io.wisoft.iotplatform.device.domain.Device;
import io.wisoft.iotplatform.device.domain.Device.DeviceStatus;
import io.wisoft.iotplatform.device.domain.Sensor;
import io.wisoft.iotplatform.device.exception.CncNotFoundException;
import io.wisoft.iotplatform.device.exception.DeviceDuplicatedException;
import io.wisoft.iotplatform.device.exception.DeviceNotFoundException;
import io.wisoft.iotplatform.device.repository.ActuatorRepository;
import io.wisoft.iotplatform.device.repository.DeviceRepository;
import io.wisoft.iotplatform.device.repository.SensorRepository;
import io.wisoft.iotplatform.device.service.dto.DeviceDto.DeviceInitialize;
import io.wisoft.iotplatform.device.service.dto.DeviceDto.DeviceRegister;
import io.wisoft.iotplatform.device.service.dto.DeviceDto.DeviceUpdate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@Transactional
public class DeviceService {

  @Value("${multiplicationHost}")
  private String API_GATE_HOST;
  private DeviceRepository deviceRepository;
  private SensorRepository sensorRepository;
  private ActuatorRepository actuatorRepository;
  private ModelMapper modelMapper;
  private RestTemplate restTemplate;

  public DeviceService(final DeviceRepository deviceRepository, final SensorRepository sensorRepository, final ActuatorRepository actuatorRepository, final ModelMapper modelMapper, final RestTemplate restTemplate) {
    this.deviceRepository = deviceRepository;
    this.sensorRepository = sensorRepository;
    this.actuatorRepository = actuatorRepository;
    this.modelMapper = modelMapper;
    this.restTemplate = restTemplate;
  }

  public Device register(final DeviceRegister registerDto) {
    checkExistingCnc(registerDto.getCncId());
    checkDuplicateName(registerDto.getName());
    checkDuplicateIpAddress(registerDto.getIpAddress());

    final Device device = modelMapper.map(registerDto, Device.class);

    return deviceRepository.save(device);
  }

  public Device update(final UUID id, final DeviceUpdate updateDto) {
    checkExistingCnc(updateDto.getCncId());

    final Device device = getDevice(id);
    device.setName(updateDto.getName());
    device.setLocation(updateDto.getLocation());
    device.setProtocol(updateDto.getProtocol());
    device.setStatus(updateDto.getStatus());
    device.setBattery(updateDto.getBattery());
    device.setIpAddress(updateDto.getIpAddress());
    device.setDescription(updateDto.getDescription());
    device.setSleep(updateDto.isSleep());
    device.setCncId(updateDto.getCncId());
    device.setUpdated(LocalDateTime.now());

    return deviceRepository.save(device);
  }

  public Device initialize(final UUID id, final DeviceInitialize dto) {
    final Device device = getDevice(id);
    device.setStatus(dto.getStatus());
    device.setIpAddress(dto.getIpAddress());
    device.setUpdated(LocalDateTime.now());

    return deviceRepository.save(device);
  }

  public void remove(final UUID id) {
    deviceRepository.delete(getDevice(id));
  }

  public Device getDevice(final UUID id) {
    return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(String.valueOf(id)));
  }

  public Device getDeviceBySerial(final String serial) {
    return deviceRepository.findBySerial(serial).orElseThrow(() -> new DeviceNotFoundException(serial));
  }

  public Device getDeviceName(final String name) {
    return deviceRepository.findByName(name).orElseThrow(() -> new DeviceNotFoundException(name));
  }

  public List<Device> getDeviceByStatus(final String status) {
    final DeviceStatus deviceStatus = DeviceStatus.valueOf(status);
    final List<Device> devices = deviceRepository.findByStatus(deviceStatus);
    if (devices == null) {
      throw new DeviceNotFoundException(status);
    }

    return devices;
  }

  public Device getDeviceByActuatorId(final UUID actuatorId) {
    return deviceRepository.findByActuatorId(actuatorId).orElseThrow(() -> new DeviceNotFoundException(String.valueOf(actuatorId)));
  }

  public List<Sensor> getSensorsByDeviceId(final UUID deviceId) {
    deviceRepository.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException(String.valueOf(deviceId)));
    return sensorRepository.findByDeviceId(deviceId);
  }

  public List<Actuator> getActuatorsByDeviceId(final UUID deviceId) {
    deviceRepository.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException(String.valueOf(deviceId)));
    return actuatorRepository.getByDeviceId(deviceId);
  }

  public Page<Device> getDevices(final Pageable pageable) {
    return deviceRepository.findAll(pageable);
  }

  public List<Device> getDeviceByCncId(final UUID cncId) {
    return deviceRepository.findByCncId(cncId);
  }

  private void checkExistingCnc(final UUID cncId) {
    final ResponseEntity<Object> response = restTemplate.getForEntity(API_GATE_HOST + "/cncs/{id}", Object.class, cncId);
    if (response.getStatusCode() != OK) {
      throw new CncNotFoundException(String.valueOf(cncId));
    }
  }

  private void checkDuplicateName(final String name) {
    deviceRepository.findByName(name).ifPresent(device -> {
      log.error("Device Duplicated Name Exception: {}", name);
      throw new DeviceDuplicatedException(name);
    });
  }

  private void checkDuplicateIpAddress(final String ipAddress) {
    deviceRepository.findByIpAddress((ipAddress)).ifPresent(device -> {
      log.error("Device Duplicated IP Address Exception: {}", ipAddress);
      throw new DeviceDuplicatedException(ipAddress);
    });
  }

}
