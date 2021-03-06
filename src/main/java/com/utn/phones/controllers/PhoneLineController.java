package com.utn.phones.controllers;

import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.City;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class PhoneLineController {

  private PhoneLineService phoneLineService;

  @Autowired
  public PhoneLineController(PhoneLineService phoneLineService) {
    this.phoneLineService = phoneLineService;
  }

  public List<PhoneLine> findAll() {
    return this.phoneLineService.findAll();
  }

  public PhoneLine save(PhoneLineDto phoneLine, Integer idClient)
      throws PhoneLineAlreadyExists, ClientNotFoundException {
    return phoneLineService.save(phoneLine,idClient);
  }

  public void deleteById(Integer id) throws PhoneLineNotExists {
    this.phoneLineService.deleteById(id);
  }

  public PhoneLine update(PhoneLineDto phoneLineDto, Integer id)
      throws PhoneLineNotExists {
    return this.phoneLineService.update(phoneLineDto, id);
  }

}
