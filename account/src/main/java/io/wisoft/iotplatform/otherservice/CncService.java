package io.wisoft.iotplatform.otherservice;

import io.wisoft.iotplatform.otherservice.dto.CncResponse;
import io.wisoft.iotplatform.otherservice.exception.ServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CncService {

  @Value("${multiplicationHost}")
  private String API_GATEWAY_HOST;
  private final RestTemplate restTemplate;

  public CncService(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<CncResponse> getCncByGroupId(final UUID groupId) {
    final ResponseEntity<CncResponse[]> response = restTemplate.getForEntity(API_GATEWAY_HOST + "/groups/search/byGroupId?groupId={id}", CncResponse[].class, groupId);

    if (response.getStatusCode().is5xxServerError()) {
      throw new ServiceNotAvailableException("CnC", response.getStatusCode().toString());
    }

    final CncResponse[] cncResponses = response.getBody();

    if (cncResponses == null || cncResponses.length == 0) {
      return new ArrayList<>();
    }

    return Arrays.asList(cncResponses);
  }

}
