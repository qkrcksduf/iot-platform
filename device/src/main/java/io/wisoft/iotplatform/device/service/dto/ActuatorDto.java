package io.wisoft.iotplatform.device.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActuatorDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ActuatorRegister {

    @NotNull
    @Size(max = 12)
    private String serial;

    @NotNull
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
  public static class ActuatorResponse {

    private UUID id;
    private String serial;
    private String name;
    private UUID deviceId;
    private UUID modelId;
    private LocalDateTime joined;
    private LocalDateTime updated;
    private String description;

  }

  @Getter
  @Setter
  public static class ActuatorUpdate {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    private UUID modelId;

  }

}
