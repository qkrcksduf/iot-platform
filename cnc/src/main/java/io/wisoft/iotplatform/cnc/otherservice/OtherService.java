package io.wisoft.iotplatform.cnc.otherservice;

import io.wisoft.iotplatform.cnc.exception.ActuatorNotFoundException;
import io.wisoft.iotplatform.cnc.exception.GroupNotFoundException;
import io.wisoft.iotplatform.cnc.otherservice.dto.DeviceResponse;
import io.wisoft.iotplatform.cnc.otherservice.exception.ServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
public class OtherService {

  @Value("${multiplicationHost}")
  private String API_GATEWAY_HOST;
  private RestTemplate restTemplate;

  public OtherService(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void checkExistingGroup(final UUID id) {
    ResponseEntity<Object> response = restTemplate.getForEntity(API_GATEWAY_HOST + "/groups/{groupId}", Object.class, id);

    if (response.getStatusCode() == BAD_REQUEST) {
      throw new GroupNotFoundException(String.valueOf(id));
    }
    if (response.getStatusCode().is5xxServerError()) {
      throw new ServiceNotAvailableException("Account", response.getStatusCode().toString());
    }
  }

  public DeviceResponse getDeviceListByActuatorId(final UUID id) {
    final ResponseEntity<DeviceResponse> response = restTemplate.getForEntity(API_GATEWAY_HOST + "/devices/search/actuator-id?q={id}", DeviceResponse.class, id);

    if (response.getStatusCode() == BAD_REQUEST) {
      throw new ActuatorNotFoundException(String.valueOf(id));
    }
    if (response.getStatusCode().is5xxServerError()) {
      throw new ServiceNotAvailableException("Device", response.getStatusCode().toString());
    }

    return response.getBody();
  }

  public List<DeviceResponse> getDeviceListByCncId(final UUID id) {
    ResponseEntity<DeviceResponse[]> response = restTemplate.getForEntity(API_GATEWAY_HOST + "/devices/search/byCncId?cncId={id}", DeviceResponse[].class, id);

    if (response.getStatusCode().is5xxServerError()) {
      throw new ServiceNotAvailableException("Device", response.getStatusCode().toString());
    }

    return Arrays.asList(response.getBody());
  }

}
