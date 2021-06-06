package io.wisoft.iotplatform.model.controller;

import io.wisoft.iotplatform.model.common.BindingErrorResponse;
import io.wisoft.iotplatform.model.common.ErrorResponse;
import io.wisoft.iotplatform.model.exception.ModelTypeDuplicatedException;
import io.wisoft.iotplatform.model.exception.ModelTypeNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ModelTypeControllerAdvice {

  @ExceptionHandler(value = ModelTypeDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleModelTypeDuplicatedException(final ModelTypeDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ModelTypeController-X001");
    errorResponse.setTitle("Duplicated Model Type Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 시스템에 등록된 모델 타입입니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = ModelTypeNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleModelNotFoundException(final ModelTypeNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ModelTypeController-X002");
    errorResponse.setTitle("Model Type Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 모델 타입이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("ModelTypeController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
