package io.wisoft.iotplatform.group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("serial")
public class GroupNotFoundException extends RuntimeException {

  @Getter
  final String information;

}
