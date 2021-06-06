package io.wisoft.iotplatform.accountgroup.controller;

import io.wisoft.iotplatform.account.exception.AccountBadCredentialsExceptionException;
import io.wisoft.iotplatform.account.exception.AccountDuplicatedException;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.accountgroup.exception.AccountGroupNotFoundException;
import io.wisoft.iotplatform.common.BindingErrorResponse;
import io.wisoft.iotplatform.common.ErrorResponse;
import io.wisoft.iotplatform.group.exception.GroupNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice("io.wisoft.iotplatform.accountgroup.controller")
public class AccountGroupControllerAdvice {

  @ExceptionHandler(AccountGroupNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleAccountGroupNotFoundException(final AccountGroupNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("AccountGroupController-X002");
    errorResponse.setTitle("AccountGroup Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 내용이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleAccountNotFoundException(final AccountNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("AccountGroupController-X003");
    errorResponse.setTitle("Account Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 내용이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(GroupNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleGroupNotFoundException(final GroupNotFoundException e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("AccountGroupController-X004");
    errorResponse.setTitle("Group Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 내용이 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public BindingErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("AccountGroupController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
