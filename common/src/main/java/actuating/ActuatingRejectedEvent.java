package actuating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class ActuatingRejectedEvent {

  private UUID actuatingId;

  private UUID actuatorId;

  private String actuatingValue;

  private String result;

}
