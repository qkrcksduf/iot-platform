package io.wisoft.iotplatform.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AccountBadCredentialsExceptionException extends RuntimeException {

  @Getter
  final String information;

}
