package com.utn.phones.exceptions.callExceptions;

import com.utn.phones.exceptions.generalExceptions.ResourceNotFoundException;

public class CallNotFoundException extends ResourceNotFoundException {

  @Override
  public String getMessage() {
    return super.getMessage() + "Call not found";
  }
}
