package io.wisoft.iotplatform.device.controller;

import io.wisoft.iotplatform.device.common.BindingErrorResponse;
import io.wisoft.iotplatform.device.common.ErrorResponse;
import io.wisoft.iotplatform.device.exception.ActuatorDuplicatedException;
import io.wisoft.iotplatform.device.exception.ActuatorNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ActuatorControllerAdvice {

  @ExceptionHandler(value = ActuatorDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleActuatorDuplicatedException(final ActuatorDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ActuatorController-X001");
    errorResponse.setTitle("Duplicated Actuator Information Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 시스템에 등록된 액추에이터입니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = ActuatorNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleActuatorNotFoundException(final ActuatorNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ActuatorController-X002");
    errorResponse.setTitle("Actuator Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 액츄에이터가 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("ActuatorController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
