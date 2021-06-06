package io.wisoft.iotplatform.device.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ModelNotFoundException extends RuntimeException {

  @Getter
  final String information;

}
