package io.wisoft.iotplatform.device.domain;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@ToString
@Data
public class ActuatorValidationCheckCommand {

    @TargetAggregateIdentifier
    private UUID actuatorId;

    private UUID deviceId;

    private UUID modelId;

    private String actuatorName;

    private String deviceName;

    private String actuatorStatus;

    private String description;

    private String actuatingValue;

    public ActuatorValidationCheckCommand(UUID actuatorId, String actuatorStatus, String actuatingValue, String description) {
        this.actuatorId = actuatorId;
        this.actuatorStatus = actuatorStatus;
        this.actuatingValue = actuatingValue;
        this.description = description;
    }

}
