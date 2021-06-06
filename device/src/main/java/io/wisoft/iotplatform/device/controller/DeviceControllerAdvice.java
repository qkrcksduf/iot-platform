package io.wisoft.iotplatform.device.controller;

import io.wisoft.iotplatform.device.common.BindingErrorResponse;
import io.wisoft.iotplatform.device.common.ErrorResponse;
import io.wisoft.iotplatform.device.exception.DeviceDuplicatedException;
import io.wisoft.iotplatform.device.exception.DeviceNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class DeviceControllerAdvice {

  @ExceptionHandler(value = DeviceDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleDeviceDuplicatedException(final DeviceDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("DeviceController-X001");
    errorResponse.setTitle("Duplicated Device Information Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 시스템에 등록된 장치입니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = DeviceNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleDeviceNotFoundException(final DeviceNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("DeviceController-X002");
    errorResponse.setTitle("Device Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 장치가 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("DeviceController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
