package io.wisoft.iotplatform.device.domain;

import io.wisoft.iotplatform.device.event.TestEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ActuatorAggregate {

    @AggregateIdentifier
    private UUID id;

    @CommandHandler
    public ActuatorAggregate(final ActuatorValidationCheckCommand command) {
        log.debug("handling {}", command);

        this.id = command.getActuatorId();

        apply(new TestEvent(command.getActuatorId()));
    }

}
