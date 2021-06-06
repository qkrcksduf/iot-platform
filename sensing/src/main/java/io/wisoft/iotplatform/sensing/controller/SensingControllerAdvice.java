package io.wisoft.iotplatform.sensing.controller;

import io.wisoft.iotplatform.sensing.common.BindingErrorResponse;
import io.wisoft.iotplatform.sensing.common.ErrorResponse;
import io.wisoft.iotplatform.sensing.exception.SensingNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class SensingControllerAdvice {

  @ExceptionHandler(value = SensingNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleSensingValueNotFoundException(final SensingNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("SensingController-X001");
    errorResponse.setTitle("Sensing Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 내용이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("SensingController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
