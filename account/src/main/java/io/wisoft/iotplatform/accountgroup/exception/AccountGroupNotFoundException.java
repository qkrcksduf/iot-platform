package io.wisoft.iotplatform.accountgroup.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class AccountGroupNotFoundException extends RuntimeException {

  @Getter
  final String information;

}
