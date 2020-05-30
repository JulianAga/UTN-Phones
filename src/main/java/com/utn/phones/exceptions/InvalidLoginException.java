package com.utn.phones.exceptions;

public class InvalidLoginException extends Throwable {

  public InvalidLoginException(Throwable cause) {
    super(cause);
  }

  public String getMessage() {
    return "Invalid login";
  }
}
