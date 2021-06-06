package io.wisoft.iotplatform.device.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class DeviceNotOperatedEvent {

  private UUID actuatingId;

  private UUID actuatorId;

  private String actuatorName;

  private String actuatingValue;

  private String actuatingResult;

  private String description;

}