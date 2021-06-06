package io.wisoft.iotplatform.device.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class DeviceDuplicatedException extends RuntimeException {

  @Getter
  final String information;

}