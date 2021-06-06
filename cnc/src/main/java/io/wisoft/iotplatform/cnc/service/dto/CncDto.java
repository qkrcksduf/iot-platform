package io.wisoft.iotplatform.cnc.service.dto;

import io.wisoft.iotplatform.cnc.domain.CncStatus;
import io.wisoft.iotplatform.cnc.otherservice.dto.DeviceResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CncDto {

  @Getter
  @Setter
  @Builder
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CncRegister {

    @NotBlank
    @Size(max = 12)
    private String serial;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank
    @Size(min = 5, max = 30)
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CncStatus status;

    @NotBlank
    @Size(max = 40)
    private String ipAddress;

    @Size(max = 250)
    private String description;

    @NotNull
    private UUID groupId;

  }

  @ToString
  @Getter
  @Setter
  public static class CncResponse {

    private UUID id;
    private String serial;
    private String name;
    private String location;
    private CncStatus status;
    private UUID groupId;
    private String ipAddress;
    private LocalDateTime joined;
    private LocalDateTime updated;
    private String description;

  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class CncDevicesResponse {

    private UUID id;
    private List<DeviceResponse> devices;

  }

  @Getter
  @Setter
  public static class CncDevice {

    private UUID id;
    private DeviceResponse devices;

  }

  @Getter
  @Setter
  public static class CncUpdate {

    @NotBlank
    @Size(min = 5, max = 15)
    private String name;

    @NotBlank
    @Size(min = 5, max = 30)
    private String location;

    @NotNull
    private CncStatus status;

    @NotBlank
    @Size(max = 40)
    private String ipAddress;

    @Size(max = 250)
    private String description;

  }

  @Getter
  @Setter
  public static class CncInitialize {

    @NotBlank
    @Size(max = 40)
    private String ipAddress;

    @NotNull
    private CncStatus status;

  }

}
