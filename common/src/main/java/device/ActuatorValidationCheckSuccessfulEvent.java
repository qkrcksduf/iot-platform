package device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
@Builder
public class ActuatorValidationCheckSuccessfulEvent {

  private UUID actuatorId;

  private UUID deviceId;

  private UUID modelId;

  private String actuatorName;

  private String deviceName;

  private String actuatorStatus;

  private String description;

  private String actuatingValue;

}
