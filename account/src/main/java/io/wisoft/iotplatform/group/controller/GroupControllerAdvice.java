package io.wisoft.iotplatform.group.controller;

import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.accountgroup.exception.AccountGroupNotFoundException;
import io.wisoft.iotplatform.common.BindingErrorResponse;
import io.wisoft.iotplatform.common.ErrorResponse;
import io.wisoft.iotplatform.group.exception.GroupDuplicatedException;
import io.wisoft.iotplatform.group.exception.GroupNotFoundException;
import io.wisoft.iotplatform.otherservice.exception.ServiceNotAvailableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice("io.wisoft.iotplatform.accountgroup.controller")
public class GroupControllerAdvice {

  @ExceptionHandler(value = GroupDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleGroupDuplicatedException(final GroupDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("GroupController-X001");
    errorResponse.setTitle("Duplicated Group Name Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은 이미 시스템에 등록된 그룹 이름입니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = GroupNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleGroupNotFoundException(final GroupNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("GroupController-X002");
    errorResponse.setTitle("Group Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 그룹 이름이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(value = ServiceNotAvailableException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorResponse handleMicroserviceNotAvailableException(final ServiceNotAvailableException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("GroupController-X003");
    errorResponse.setTitle("Service Not Available Exception");
    errorResponse.setMessage("요청 처리 중 " + e.getServiceName() + " 마이크로서비스에서 " + e.getStatusCode() + " 에러가 발생하였습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public BindingErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("GroupController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
