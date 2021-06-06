package io.wisoft.iotplatform.cnc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class GroupNotFoundException extends RuntimeException {

  @Getter
  final String information;

}