//package io.wisoft.iotplatform.device.handler;
//
//import device.ActuatorValidationCheckSuccessfulEvent;
//import device.ActuatorValidationCheckUnsuccessfulEvent;
//import io.wisoft.iotplatform.device.domain.ActuatorValidationCheckCommand;
//import lombok.extern.slf4j.Slf4j;
//import org.axonframework.commandhandling.CommandHandler;
//import org.springframework.stereotype.Component;
//
//import static org.axonframework.modelling.command.AggregateLifecycle.apply;
//
//@Slf4j
//@Component
//public class ActuatorValidationHandler {
//
//    @CommandHandler
//    protected void checkExistActuator(ActuatorValidationCheckCommand command) {
//
//        log.debug("handling {}", command);
//
//        if (command.getActuatorStatus().equals("impossible")) {
//            apply(ActuatorValidationCheckUnsuccessfulEvent.builder()
//                    .actuatorId(command.getActuatorId())
//                    .actuatorStatus(command.getActuatorStatus())
//                    .actuatingValue(command.getActuatingValue())
//                    .description(command.getDescription())
//                    .build());
//
//        } else {
//            apply(ActuatorValidationCheckSuccessfulEvent.builder()
//                    .actuatorId(command.getActuatorId())
//                    .actuatorName(command.getActuatorName())
//                    .actuatorStatus(command.getActuatorStatus())
//                    .deviceId(command.getDeviceId())
//                    .deviceName(command.getDeviceName())
//                    .description(command.getDescription())
//                    .actuatingValue(command.getActuatingValue())
//                    .modelId(command.getModelId())
//                    .build());
//        }
//    }
//
//}
