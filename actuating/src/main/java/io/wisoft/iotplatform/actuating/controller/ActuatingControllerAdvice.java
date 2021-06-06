package io.wisoft.iotplatform.actuating.controller;

import io.wisoft.iotplatform.actuating.common.BindingErrorResponse;
import io.wisoft.iotplatform.actuating.common.ErrorResponse;
import io.wisoft.iotplatform.actuating.exception.ActuatingNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ActuatingControllerAdvice {

  @ExceptionHandler(value = ActuatingNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleDeviceActuatorNotFoundException(
      final ActuatingNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ActuatingController-X001");
    errorResponse.setTitle("Actuating Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 내용이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("ActuatingController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
