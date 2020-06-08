package com.utn.phones.exceptions.clientExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException() {
    super();
  }

  public ClientNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientNotFoundException(Throwable cause) {
    super(cause);
  }

  protected ClientNotFoundException(String message) {
    super(message);
  }
}
