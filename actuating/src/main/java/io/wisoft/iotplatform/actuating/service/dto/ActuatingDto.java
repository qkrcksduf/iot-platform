package io.wisoft.iotplatform.actuating.service.dto;

import io.wisoft.iotplatform.actuating.domain.Actuating.Status;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActuatingDto {

  @Getter
  @Setter
  @Builder
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ActuatingRegister {

    @NotNull
    private UUID actuatorId;

    private String actuatingValue;

    private Status result;

  }

  @Getter
  @Setter
  public static class ActuatingResponse {

    private UUID id;
    private UUID actuatorId;
    private Date actuatingTime;
    private String actuatingValue;
    private Status result;

  }

  @Getter
  @Setter
  public static class ActuatingUpdate {

    @NotNull
    private Status result;

  }

  @Getter
  @Setter
  @ToString
  public static class ActuatingRegisterToCnc {

    private UUID id;
    private UUID actuatorId;
    private String actuatingValue;
    private Status result;

  }

}
