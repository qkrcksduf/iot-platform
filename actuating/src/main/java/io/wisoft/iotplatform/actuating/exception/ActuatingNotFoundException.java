package io.wisoft.iotplatform.actuating.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ActuatingNotFoundException extends RuntimeException {

  @Getter
  final String information;

}
