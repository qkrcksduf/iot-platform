package io.wisoft.iotplatform.cnc.otherservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeviceResponse {

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

  public enum DeviceType {
    AN, SN, SAN
  }

  public enum DeviceStatus {
    ACTIVE, INACTIVE, INSTALL
  }

}
