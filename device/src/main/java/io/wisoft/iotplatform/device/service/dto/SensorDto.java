package io.wisoft.iotplatform.device.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDto {

  @Getter
  @Setter
  @Builder
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SensorRegister {

    @NotBlank
    @Size(max = 12)
    private String serial;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    private UUID modelId;

    @NotNull
    private UUID deviceId;

  }

  @Getter
  @Setter
  public static class SensorResponse {

    private UUID id;
    private String serial;
    private String name;
    private LocalDateTime joined;
    private LocalDateTime updated;
    private String description;
    private UUID modelId;
    private UUID deviceId;

  }

  @Getter
  @Setter
  public static class SensorUpdate {

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    private UUID modelId;

  }

}
