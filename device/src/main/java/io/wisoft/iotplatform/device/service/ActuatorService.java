package io.wisoft.iotplatform.device.service;

import io.wisoft.iotplatform.device.domain.Actuator;
import io.wisoft.iotplatform.device.domain.Device;
import io.wisoft.iotplatform.device.exception.ActuatorDuplicatedException;
import io.wisoft.iotplatform.device.exception.ActuatorNotFoundException;
import io.wisoft.iotplatform.device.exception.ModelNotFoundException;
import io.wisoft.iotplatform.device.repository.ActuatorRepository;
import io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorRegister;
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
import java.util.UUID;

import static io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorUpdate;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@Transactional
public class ActuatorService {

  @Value("${multiplicationHost}")
  private String API_GATE_HOST;
  private DeviceService deviceService;
  private ActuatorRepository actuatorRepository;
  private ModelMapper modelMapper;
  private RestTemplate restTemplate;

  public ActuatorService(final DeviceService deviceService, final ActuatorRepository actuatorRepository, final ModelMapper modelMapper, final RestTemplate restTemplate) {
    this.deviceService = deviceService;
    this.actuatorRepository = actuatorRepository;
    this.modelMapper = modelMapper;
    this.restTemplate = restTemplate;
  }

  public Actuator register(final ActuatorRegister registerDto) {
    checkDuplicateName(registerDto.getName());
    checkExistingModel(registerDto.getModelId());

    final Device device = deviceService.getDevice(registerDto.getDeviceId());
    final Actuator actuator = modelMapper.map(registerDto, Actuator.class);
    actuator.setDevice(device);

    return actuatorRepository.save(actuator);
  }

  public Actuator update(final UUID id, final ActuatorUpdate updateDto) {
    checkExistingModel(updateDto.getModelId());

    final Actuator actuator = getActuator(id);
    actuator.setName(updateDto.getName());
    actuator.setModelId(updateDto.getModelId());
    actuator.setDescription(updateDto.getDescription());
    actuator.setUpdated(LocalDateTime.now());

    return actuatorRepository.save(actuator);
  }

  public void remove(final UUID id) {
    actuatorRepository.delete(getActuator(id));
  }

  public Actuator getActuator(final UUID id) {
    return actuatorRepository.findById(id).orElseThrow(() -> new ActuatorNotFoundException(String.valueOf(id)));
  }

  public Actuator getActuatorBySerial(final String serial) {
    return actuatorRepository.findBySerial(serial).orElseThrow(() -> new ActuatorNotFoundException(serial));
  }

  public Actuator getActuatorByName(final String name) {
    return actuatorRepository.findByName(name).orElseThrow(() -> new ActuatorNotFoundException(name));
  }

  public Page<Actuator> getActuators(final Pageable pageable) {
    return actuatorRepository.findAll(pageable);
  }

  private void checkDuplicateName(final String name) {
    actuatorRepository.findByName(name).ifPresent(actuator -> {
      log.error("Actuator Duplicated Name Exception: {}", name);
      throw new ActuatorDuplicatedException(name);
    });
  }

  private void checkExistingModel(final UUID modelId) {
    ResponseEntity<Object> response = this.restTemplate.getForEntity(API_GATE_HOST + "/models/{id}", Object.class, modelId);
    if (response.getStatusCode() != OK) {
      throw new ModelNotFoundException(String.valueOf(modelId));
    }
  }

}
