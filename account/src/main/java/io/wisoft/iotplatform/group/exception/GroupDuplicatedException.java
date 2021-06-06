package io.wisoft.iotplatform.group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class GroupDuplicatedException extends RuntimeException {

  @Getter
  final String information;

}