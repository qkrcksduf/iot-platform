package io.wisoft.iotplatform.sensing.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SensingDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SensingRegister {

    @NotNull
    private UUID sensorId;

    private HashMap<String, String> sensingValue;

  }

  @Getter
  @Setter
  public static class SensingResponse {

    private UUID id;
    private UUID sensorId;
    private LocalDateTime sensingTime;
    private HashMap<String, String> sensingValue;

  }

}
