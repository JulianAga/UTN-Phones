package com.utn.phones.services;

import com.utn.phones.dto.PhoneLineDto;
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

  @Autowired
  public PhoneLineService(final PhoneLineRepository phoneLineRepository) {
    this.phoneLineRepository = phoneLineRepository;
  }

  public List<PhoneLine> findAll() {
    return this.phoneLineRepository.findAll().stream()
        .filter(phoneLine -> phoneLine.getActive() == Boolean.TRUE)
        .collect(Collectors.toList());
  }

  public PhoneLine save(PhoneLine phoneLine, City city) throws PhoneLineAlreadyExists {
    if (this.phoneLineRepository.findByNumber(phoneLine.getNumber()) != null) {
      throw new PhoneLineAlreadyExists();
    }
    phoneLine.setNumber(city.getPrefix() + phoneLine.getNumber());
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

  public PhoneLine update(PhoneLineDto phoneLineDto, City city, Integer id)
      throws PhoneLineNotExists {
    PhoneLine phoneLine = this.findById(id);
    phoneLine.setNumber(city.getPrefix() + phoneLineDto.getNumber());
    phoneLine.setSuspended(phoneLineDto.getSuspended());
    return this.phoneLineRepository.save(phoneLine);
  }

}
