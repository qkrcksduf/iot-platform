package io.wisoft.iotplatform.actuating.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class CncNotFoundException extends RuntimeException {

  @Getter
  final String information;

}