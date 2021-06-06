package io.wisoft.iotplatform.model.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

  private String code;
  private String title;
  private String message;

}
