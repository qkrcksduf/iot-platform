package io.wisoft.iotplatform.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class ModelDuplicatedException extends RuntimeException {

  @Getter
  final String information;

}