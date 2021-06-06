package io.wisoft.iotplatform.cnc.controller;

import io.wisoft.iotplatform.cnc.common.BindingErrorResponse;
import io.wisoft.iotplatform.cnc.common.ErrorResponse;
import io.wisoft.iotplatform.cnc.exception.ActuatorNotFoundException;
import io.wisoft.iotplatform.cnc.exception.CncDuplicatedException;
import io.wisoft.iotplatform.cnc.exception.CncNotFoundException;
import io.wisoft.iotplatform.cnc.exception.GroupNotFoundException;
import io.wisoft.iotplatform.cnc.otherservice.exception.ServiceNotAvailableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice("io.wisoft.iotplatform.cnc.controller")
public class CncControllerAdvice {

  @ExceptionHandler(CncDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleCncDuplicatedException(final CncDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("CncController-X001");
    errorResponse.setTitle("Duplicated CnC Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 등록된 CnC입니다.");
    return errorResponse;
  }

  @ExceptionHandler(CncNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleCncNotFoundException(final CncNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("CncController-X002");
    errorResponse.setTitle("CnC Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 CnC가 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(ServiceNotAvailableException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorResponse handleMicroserviceNotAvailableException(final ServiceNotAvailableException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("CncController-X003");
    errorResponse.setTitle("Service Not Available Exception");
    errorResponse.setMessage("요청 처리 중 " + e.getServiceName() + " 마이크로서비스에서 " + e.getStatusCode() + " 에러가 발생하였습니다.");
    return errorResponse;
  }

  @ExceptionHandler(ActuatorNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleActuatorNotFoundException(final ActuatorNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("CncController-X004");
    errorResponse.setTitle("Actuator Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 엑츄에이터가 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(GroupNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleGroupNotFoundException(final GroupNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("CncController-X005");
    errorResponse.setTitle("Group Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 그룹이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("CncController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
