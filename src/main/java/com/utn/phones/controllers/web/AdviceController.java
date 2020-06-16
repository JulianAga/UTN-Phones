package com.utn.phones.controllers.web;

import com.utn.phones.dto.ErrorResponseDto;
import com.utn.phones.exceptions.loginExceptions.InvalidLoginException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.exceptions.loginExceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(InvalidLoginException.class)
  public ErrorResponseDto handleLoginException(InvalidLoginException exception) {
    return new ErrorResponseDto(1, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ErrorResponseDto handleValidationException(ValidationException exception) {
    return new ErrorResponseDto(2, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserNotExistException.class)
  public ErrorResponseDto handleUserNotExists(UserNotExistException exception) {
    return new ErrorResponseDto(3, exception.getMessage());
  }


}