package io.wisoft.iotplatform.model.controller;

import io.wisoft.iotplatform.model.common.BindingErrorResponse;
import io.wisoft.iotplatform.model.common.ErrorResponse;
import io.wisoft.iotplatform.model.exception.ModelDuplicatedException;
import io.wisoft.iotplatform.model.exception.ModelNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ModelControllerAdvice {

  @ExceptionHandler(value = ModelDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleModelDuplicatedException(final ModelDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ModelController-X001");
    errorResponse.setTitle("Duplicated Model Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 시스템에 등록된 모델입니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = ModelNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleModelNotFoundException(final ModelNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("ModelController-X002");
    errorResponse.setTitle("Model Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 모델이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("ModelController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
