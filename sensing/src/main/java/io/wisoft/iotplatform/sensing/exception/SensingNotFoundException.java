package io.wisoft.iotplatform.sensing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SensingNotFoundException extends RuntimeException {

  @Getter
  final String information;

}
