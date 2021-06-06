package device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.units.qual.A;

import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
@Builder
public class ActuatorValidationCheckUnsuccessfulEvent {

  private UUID actuatorId;

  private String actuatorStatus;

  private String description;

  private String actuatingValue;

}
