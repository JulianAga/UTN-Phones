package com.utn.phones.exceptions.clientExceptions;

import com.utn.phones.exceptions.generalExceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ClientNotFoundException extends ResourceNotFoundException {

  @Override
  public String getMessage() {
    return super.getMessage() + " invalid client id not found";
  }
}
