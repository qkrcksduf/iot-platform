package io.wisoft.iotplatform.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class ModelTypeDuplicatedException extends RuntimeException {

  @Getter
  final String information;

}