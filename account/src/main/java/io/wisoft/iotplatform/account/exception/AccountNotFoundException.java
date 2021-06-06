package io.wisoft.iotplatform.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

  @Getter
  final String information;

}