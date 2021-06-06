package io.wisoft.iotplatform.actuating.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CncResponse {

  private UUID id;
  private String serial;
  private String name;
  private String location;
  private String status;
  private UUID groupId;
  private String ipAddress;
  private LocalDateTime joined;
  private LocalDateTime updated;
  private String description;

}
