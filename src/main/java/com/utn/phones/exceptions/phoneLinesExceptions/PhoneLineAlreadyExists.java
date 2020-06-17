package com.utn.phones.exceptions.phoneLinesExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneLineAlreadyExists extends Exception {

    public String getMessage() {
      return "This phone line is already in use";
    }

}