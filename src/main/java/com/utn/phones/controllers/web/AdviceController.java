package com.utn.phones.controllers.web;

import com.utn.phones.dto.ErrorResponseDto;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import com.utn.phones.exceptions.loginExceptions.InvalidLoginException;
import com.utn.phones.exceptions.loginExceptions.UserNotExistException;
import com.utn.phones.exceptions.loginExceptions.ValidationException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
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

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidDateException.class)
  public ErrorResponseDto handleInvalidDateException(InvalidDateException exception) {
    return new ErrorResponseDto(4, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(CityNotFoundException.class)
  public ErrorResponseDto handleCityNotFoundException(CityNotFoundException exception) {
    return new ErrorResponseDto(5, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ClientNotFoundException.class)
  public ErrorResponseDto handleClientNotFoundException(ClientNotFoundException exception) {
    return new ErrorResponseDto(6, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(PhoneLineAlreadyExists.class)
  public ErrorResponseDto handlePhoneLineAlreadyExistException(PhoneLineAlreadyExists exception) {
    return new ErrorResponseDto(7, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(PhoneLineNotExists.class)
  public ErrorResponseDto handlePhoneLineNotExists(PhoneLineNotExists exception) {
    return new ErrorResponseDto(8, exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ResourceAlreadyExistException.class)
  public ErrorResponseDto handlePhoneLineNotExists(ResourceAlreadyExistException exception) {
    return new ErrorResponseDto(9, exception.getMessage());
  }





}