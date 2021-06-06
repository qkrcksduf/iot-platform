package io.wisoft.iotplatform.device.controller;

import io.wisoft.iotplatform.device.common.BindingErrorResponse;
import io.wisoft.iotplatform.device.common.ErrorResponse;
import io.wisoft.iotplatform.device.exception.SensorDuplicatedException;
import io.wisoft.iotplatform.device.exception.SensorNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class SensorControllerAdvice {

  @ExceptionHandler(value = SensorDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleSensorDuplicatedException(final SensorDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("SensorController-X001");
    errorResponse.setTitle("Duplicated Sensor Information Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 시스템에 등록된 센서입니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = SensorNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleSensorNotFoundException(final SensorNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("SensorController-X002");
    errorResponse.setTitle("Sensor Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 센서가 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("SensorController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
