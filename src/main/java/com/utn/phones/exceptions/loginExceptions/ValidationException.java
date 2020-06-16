package com.utn.phones.exceptions.loginExceptions;

public class ValidationException extends Throwable {

  public String getMessage() {
    return "Some fields are invalid";
  }

}
