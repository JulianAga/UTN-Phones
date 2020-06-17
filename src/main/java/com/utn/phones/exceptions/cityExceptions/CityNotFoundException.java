package com.utn.phones.exceptions.cityExceptions;

public class CityNotFoundException extends Exception {

  public String getMessage() {
    return "City not found";
  }

}
