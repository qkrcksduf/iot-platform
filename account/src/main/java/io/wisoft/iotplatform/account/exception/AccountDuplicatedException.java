package io.wisoft.iotplatform.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class AccountDuplicatedException extends RuntimeException {

  @Getter
  final String information;

}