package com.utn.phones.exceptions.clientExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends Exception {

  @Override
  public String getMessage() {
    return "client doesn't exist";
  }
}
