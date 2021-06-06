package io.wisoft.iotplatform.otherservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CncResponse {

  private UUID id;
  private String serial;
  private String name;
  private String location;
  private CncStatus status;
  private String ipAddress;
  private LocalDateTime joined;
  private LocalDateTime updated;
  private String description;
  private UUID groupId;

  public CncResponse(String serial, String name, String location, CncStatus status, String ipAddress, String description, UUID groupId) {
    this.serial = serial;
    this.name = name;
    this.location = location;
    this.status = status;
    this.ipAddress = ipAddress;
    this.description = description;
    this.groupId = groupId;
  }

  public enum CncStatus {
    ACTIVE, INACTIVE, INSTALL
  }

}
