package com.utn.phones.exceptions.cityExceptions;

import com.utn.phones.exceptions.generalExceptions.ResourceNotFoundException;

public class CityNotFoundException extends ResourceNotFoundException {

  public String getMessage() {
    return super.getMessage() + " invalid city id";
  }

}
