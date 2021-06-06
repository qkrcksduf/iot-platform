package io.wisoft.iotplatform.sensing.service;

import io.wisoft.iotplatform.sensing.domain.Sensing;
import io.wisoft.iotplatform.sensing.repository.SensingRepository;
import io.wisoft.iotplatform.sensing.exception.SensingNotFoundException;
import io.wisoft.iotplatform.sensing.exception.SensorNotFoundException;
import io.wisoft.iotplatform.sensing.service.dto.SensingDto.SensingRegister;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@Transactional
public class SensingService {

  @Value("${multiplicationHost}")
  private String API_GATE_HOST;
  private SensingRepository sensingRepository;
  private ModelMapper modelMapper;
  private RestTemplate restTemplate;

  public SensingService(final SensingRepository sensingRepository, final ModelMapper modelMapper, final RestTemplate restTemplate) {
    this.sensingRepository = sensingRepository;
    this.modelMapper = modelMapper;
    this.restTemplate = restTemplate;
  }

  public Sensing register(final SensingRegister sensingDto) {
    checkExistingSensor(sensingDto.getSensorId());
    final Sensing sensing = modelMapper.map(sensingDto, Sensing.class);

    return sensingRepository.save(sensing);
  }

  public void remove(final UUID id) {
    sensingRepository.delete(getSensing(id));
  }

  public Sensing getSensing(final UUID id) {
    return sensingRepository.findById(id).orElseThrow(() -> new SensingNotFoundException(String.valueOf(id)));
  }

  public List<Sensing> getSensingsBySensorId(final UUID id) {
    final List<Sensing> sensings = sensingRepository.findBySensorId(id);
    if (sensings == null || sensings.isEmpty()) {
      log.error("SensingService-getSensingsBySensorId: Not Found Exception: {}", id);
      throw new SensingNotFoundException(String.valueOf(id));
    }
    return sensings;
  }

  public Page<Sensing> getSensings(final Pageable pageable) {
    return sensingRepository.findAll(pageable);
  }

  private void checkExistingSensor(final UUID sensorId) {
    final ResponseEntity<Object> response = restTemplate.getForEntity(API_GATE_HOST + "/sensors/{id}", Object.class, sensorId);
    if (response.getStatusCode() != OK) {
      throw new SensorNotFoundException(String.valueOf(sensorId));
    }
  }

}
