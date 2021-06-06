package io.wisoft.iotplatform.sensing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class SensorNotFoundException extends RuntimeException {

  @Getter
  final String information;

}