package com.utn.phones.services;

import com.utn.phones.dto.PhoneLineDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.exceptions.clientExceptions.ClientNotFoundException;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineAlreadyExists;
import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.City;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.repositories.PhoneLineRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneLineService {

  private PhoneLineRepository phoneLineRepository;

  private CityService cityService;

  private ClientService clientService;

  @Autowired
  public PhoneLineService(final PhoneLineRepository phoneLineRepository,
      final CityService cityService, final ClientService clientService) {
    this.phoneLineRepository = phoneLineRepository;
    this.cityService = cityService;
    this.clientService = clientService;
  }

  public List<PhoneLine> findAll() {
    return this.phoneLineRepository.findAll().stream()
        .filter(phoneLine -> phoneLine.getActive() == Boolean.TRUE)
        .collect(Collectors.toList());
  }

  public PhoneLine save(PhoneLineDto phoneLineDto, Integer idClient)
      throws PhoneLineAlreadyExists, ClientNotFoundException {
    if (this.phoneLineRepository.findByNumber(phoneLineDto.getNumber()) != null) {
      throw new PhoneLineAlreadyExists();
    }

    PhoneLine phoneLine = PhoneLine.builder()
        .number(phoneLineDto.getNumber())
        .client(clientService.findById(idClient))
        .lineType(phoneLineDto.getLineType())
        .suspended(false)
        .active(true)
        .build();
    return this.phoneLineRepository.save(phoneLine);
  }

  public PhoneLine findById(Integer id) throws PhoneLineNotExists {
    return this.phoneLineRepository.findById(id)
        .orElseThrow(PhoneLineNotExists::new);
  }

  public void deleteById(Integer id) throws PhoneLineNotExists {
    PhoneLine phoneLine = this.findById(id);
    phoneLine.setActive(Boolean.FALSE);
    this.phoneLineRepository.save(phoneLine);
  }

  public PhoneLine update(PhoneLineDto phoneLineDto, Integer id)
      throws PhoneLineNotExists {
    PhoneLine phoneLine = this.findById(id);
    phoneLine.setNumber(phoneLineDto.getNumber());
    phoneLine.setSuspended(phoneLineDto.getSuspended());
    return this.phoneLineRepository.save(phoneLine);
  }

}
