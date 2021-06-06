package io.wisoft.iotplatform.actuating.service;

import io.wisoft.iotplatform.actuating.domain.Actuating;
import io.wisoft.iotplatform.actuating.repository.ActuatingRepository;
import io.wisoft.iotplatform.actuating.service.dto.ActuatingDto;
import io.wisoft.iotplatform.actuating.exception.ActuatingNotFoundException;
import io.wisoft.iotplatform.actuating.exception.CncNotFoundException;
import io.wisoft.iotplatform.actuating.service.dto.CncResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@Transactional
public class ActuatingService {

  @Value("${multiplicationHost}")
  private String API_GATE_HOST;
  private ActuatingRepository actuatingRepository;
  private ModelMapper modelMapper;
  private RestTemplate restTemplate;

  public ActuatingService(final ActuatingRepository actuatingRepository, final ModelMapper modelMapper, final RestTemplate restTemplate) {
    this.actuatingRepository = actuatingRepository;
    this.modelMapper = modelMapper;
    this.restTemplate = restTemplate;
  }

  public Actuating register(final ActuatingDto.ActuatingRegister registerDto) {
    checkExistingActuator(registerDto.getActuatorId());

    final Actuating actuating = modelMapper.map(registerDto, Actuating.class);
    actuating.setResult(Actuating.Status.RUNNING);

    final Actuating result = actuatingRepository.save(actuating);
    registerActuatingValueToCnc(result);
    return result;
  }

  public Actuating update(final UUID id, final ActuatingDto.ActuatingUpdate updateDto) {
    final Actuating actuating = getActuatingValue(id);
    actuating.setResult(updateDto.getResult());

    return actuatingRepository.save(actuating);
  }

  public void remove(final UUID id) {
    actuatingRepository.delete(getActuatingValue(id));
  }

  public Actuating getActuatingValue(final UUID id) {
    return actuatingRepository.findById(id).orElseThrow(() -> new ActuatingNotFoundException(String.valueOf(id)));
  }

  public Page<Actuating> getActuatingValues(final Pageable pageable) {
    return actuatingRepository.findAll(pageable);
  }

  private void registerActuatingValueToCnc(final Actuating actuating) {
    final ActuatingDto.ActuatingRegisterToCnc actuatingValueDto = new ActuatingDto.ActuatingRegisterToCnc();
    actuatingValueDto.setId(actuating.getId());
    actuatingValueDto.setActuatorId(actuating.getActuatorId());
    actuatingValueDto.setActuatingValue(actuating.getActuatingValue());
    actuatingValueDto.setResult(actuating.getResult());

    final String address = getCncIpAddressByActuatorId(actuatingValueDto.getActuatorId());
    final String cncAddress = "http://" + address + ":8001/api/v1/actuatings";
    final URI uri = URI.create(cncAddress);

    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ActuatingDto.ActuatingRegisterToCnc> entity = new HttpEntity<>(actuatingValueDto, headers);
    try {
      restTemplate.postForObject(uri, entity, String.class);
    } catch (final ResourceAccessException e) {
      log.error("ResourceAccessException\n{}", e.getMessage());
    }
  }

  private String getCncIpAddressByActuatorId(final UUID actuatorId) {
    final ResponseEntity<CncResponse> response = restTemplate.getForEntity(API_GATE_HOST + "/cncs/search/byActuatorId?actuatorId={id}", CncResponse.class, actuatorId);
    if (response.getStatusCode() != OK) {
      throw new CncNotFoundException(String.valueOf(actuatorId));
    }
    return response.getBody().getIpAddress();
  }

  private void checkExistingActuator(final UUID actuatorId) {
    final ResponseEntity<Object> response = restTemplate.getForEntity(API_GATE_HOST + "/actuators/{id}", Object.class, actuatorId);
    if (response.getStatusCode() != OK) {
      log.error("Actuator Not Found Exception.");
      throw new ActuatingNotFoundException("actuator");
    }
  }

}
