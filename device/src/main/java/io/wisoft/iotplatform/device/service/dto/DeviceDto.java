package io.wisoft.iotplatform.device.service.dto;

import io.wisoft.iotplatform.device.domain.Device.DeviceStatus;
import io.wisoft.iotplatform.device.domain.Device.DeviceType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
public class DeviceDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DeviceRegister {

    @NotBlank
    @Size(max = 12)
    private String serial;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    private DeviceType deviceType;

    @NotBlank
    @Size(min = 5, max = 30)
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @NotNull
    @ColumnDefault("100")
    private int battery;

    @NotBlank
    @Size(max = 40)
    private String ipAddress;

    @NotNull
    @Size(max = 4)
    private String protocol;

    @NotNull
    private boolean sleep;

    @Size(max = 250)
    private String description;

    @NotNull
    private UUID cncId;

  }

  @Getter
  @Setter
  public static class DeviceResponse {

    private UUID id;
    private String serial;
    private String name;
    private DeviceType deviceType;
    private String location;
    private DeviceStatus status;
    private int battery;
    private String ipAddress;
    private String protocol;
    private boolean sleep;
    private LocalDateTime joined;
    private LocalDateTime updated;
    private String description;
    private UUID cncId;

  }

  @Getter
  @Setter
  public static class DeviceUpdate {

    @NotBlank
    @Size(min = 5, max = 15)
    private String name;

    @NotBlank
    @Size(min = 5, max = 30)
    private String location;

    @NotNull
    private DeviceStatus status;

    @NotNull
    private int battery;

    @NotBlank
    @Size(max = 40)
    private String ipAddress;

    @NotNull
    @Size(max = 4)
    private String protocol;

    @NotNull
    private boolean sleep;

    @Size(max = 250)
    private String description;

    @NotNull
    private UUID cncId;

  }

  @Getter
  @Setter
  public static class DeviceInitialize {

    @NotBlank
    @Size(max = 40)
    private String ipAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

  }

  @Getter
  @Setter
  @Builder
  @ToString
  public static class DeviceSensorResponse {

    private UUID id;
    private String name;
    private List<SensorDto> sensors;

  }

}
