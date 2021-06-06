package io.wisoft.iotplatform.otherservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceNotAvailableException extends RuntimeException {

  final String serviceName;
  final String statusCode;

}
