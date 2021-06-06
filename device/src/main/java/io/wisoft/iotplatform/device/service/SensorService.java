package io.wisoft.iotplatform.device.service;

import io.wisoft.iotplatform.device.domain.Device;
import io.wisoft.iotplatform.device.domain.Sensor;
import io.wisoft.iotplatform.device.exception.ModelNotFoundException;
import io.wisoft.iotplatform.device.exception.SensorDuplicatedException;
import io.wisoft.iotplatform.device.exception.SensorNotFoundException;
import io.wisoft.iotplatform.device.repository.SensorRepository;
import io.wisoft.iotplatform.device.service.dto.SensorDto;
import io.wisoft.iotplatform.device.service.dto.SensorDto.SensorRegister;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class SensorService {

  @Value("${multiplicationHost}")
  private String API_GATE_WAY;
  private DeviceService deviceService;
  private SensorRepository sensorRepository;
  private ModelMapper modelMapper;
  private RestTemplate restTemplate;

  public SensorService(final DeviceService deviceService, final SensorRepository sensorRepository, final ModelMapper modelMapper, final RestTemplate restTemplate) {
    this.deviceService = deviceService;
    this.sensorRepository = sensorRepository;
    this.modelMapper = modelMapper;
    this.restTemplate = restTemplate;
  }

  public Sensor register(final SensorRegister registerDto) {
    checkDuplicateName(registerDto.getName());
    checkExistingModel(registerDto.getModelId());

    final Device device = deviceService.getDevice(registerDto.getDeviceId());
    final Sensor sensor = modelMapper.map(registerDto, Sensor.class);
    sensor.setDevice(device);

    return sensorRepository.save(sensor);
  }

  public Sensor update(final UUID id, final SensorDto.SensorUpdate updateDto) {
    checkExistingModel(updateDto.getModelId());

    final Sensor sensor = getSensor(id);
    sensor.setName(updateDto.getName());
    sensor.setModelId(updateDto.getModelId());
    sensor.setDescription(updateDto.getDescription());
    sensor.setUpdated(LocalDateTime.now());

    return sensorRepository.save(sensor);
  }

  public void remove(final UUID id) {
    sensorRepository.delete(getSensor(id));
  }

  public Sensor getSensor(final UUID id) {
    return sensorRepository.findById(id).orElseThrow(() -> new SensorNotFoundException(String.valueOf(id)));
  }

  public Sensor getSensorBySerial(final String serial) {
    return sensorRepository.findBySerial(serial).orElseThrow(() -> new SensorNotFoundException(String.valueOf(serial)));
  }

  public Sensor getSensorByName(final String name) {
    return sensorRepository.findByName(name).orElseThrow(() -> new SensorNotFoundException(String.valueOf(name)));
  }

  public List<Sensor> getSensorsByDeviceId(final UUID id) {
    final List<Sensor> sensors = sensorRepository.findByDeviceId(id);
    if (sensors == null) {
      throw new SensorNotFoundException(String.valueOf(id));
    }
    return sensors;
  }

  public Page<Sensor> getSensors(final Pageable pageable) {
    return sensorRepository.findAll(pageable);
  }

  private void checkExistingModel(final UUID modelId) {
    final ResponseEntity<Object> response = restTemplate.getForEntity(API_GATE_WAY + "/models/{id}", Object.class, modelId);
    if (response.getStatusCode() != HttpStatus.OK) {
      throw new ModelNotFoundException(String.valueOf(modelId));
    }
  }

  private void checkDuplicateName(final String name) {
    sensorRepository.findByName(name).ifPresent(sensor -> {
      log.error("Sensor Duplicated Name Exception: {}", name);
      throw new SensorDuplicatedException(name);
    });
  }

}
