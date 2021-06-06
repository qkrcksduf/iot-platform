package io.wisoft.iotplatform.sensing.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BindingErrorResponse extends ErrorResponse {

  private List<FieldErrorInfo> errors;

  public void setErrors(final List<FieldError> errors) {
    this.errors = errors
          .stream()
          .map(FieldErrorInfo::new)
          .collect(Collectors.toList());
  }

  @Getter
  @NoArgsConstructor
  class FieldErrorInfo {

    private String field;
    private String message;

    public FieldErrorInfo(final FieldError fieldError) {
      this.field = fieldError.getField();
      this.message = fieldError.getDefaultMessage();
    }

  }

}
