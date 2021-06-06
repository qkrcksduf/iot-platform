package io.wisoft.iotplatform.account.controller;

import io.wisoft.iotplatform.account.exception.AccountBadCredentialsExceptionException;
import io.wisoft.iotplatform.account.exception.AccountDuplicatedException;
import io.wisoft.iotplatform.account.exception.AccountNotFoundException;
import io.wisoft.iotplatform.common.BindingErrorResponse;
import io.wisoft.iotplatform.common.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice("io.wisoft.iotplatform.account.controller")
public class AccountControllerAdvice {

  @ExceptionHandler(AccountDuplicatedException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleAccountDuplicatedException(final AccountDuplicatedException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("AccountController-X001");
    errorResponse.setTitle("Duplicated Account Information Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "은/는 이미 시스템에 등록된 정보입니다.");
    return errorResponse;
  }

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleAccountNotFoundException(final AccountNotFoundException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("AccountController-X002");
    errorResponse.setTitle("Account Not Found Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 사용자가 없습니다.");
    return errorResponse;
  }

  @ExceptionHandler(AccountBadCredentialsExceptionException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handleAccountInvalidPasswordException(final AccountBadCredentialsExceptionException e) {
    final ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode("AccountController-X003");
    errorResponse.setTitle("Account Bad Credentials Exception");
    errorResponse.setMessage("입력하신 " + e.getInformation() + "에 해당하는 사용자의 비밀번호가 일치하지 않습니다.");
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public BindingErrorResponse handleBindingException(final MethodArgumentNotValidException e) {
    final BindingErrorResponse errorResponse = new BindingErrorResponse();
    errorResponse.setCode("AccountController-E001");
    errorResponse.setTitle("Binding Exception");
    errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
    errorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return errorResponse;
  }

}
