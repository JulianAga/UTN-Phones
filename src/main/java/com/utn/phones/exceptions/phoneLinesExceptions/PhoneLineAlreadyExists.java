package com.utn.phones.exceptions.phoneLinesExceptions;

import com.utn.phones.exceptions.generalExceptions.ResourceAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneLineAlreadyExists extends ResourceAlreadyExistException {

    public String getMessage() {
      return " this phone line is already in use";
    }

}