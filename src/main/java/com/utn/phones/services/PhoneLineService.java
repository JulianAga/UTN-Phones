package com.utn.phones.services;

import com.utn.phones.exceptions.phoneLinesExceptions.PhoneLineNotExists;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.repositories.PhoneLineRepository;
import java.util.List;
import java.util.Optional;
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
    return this.phoneLineRepository.findAll();
  }

  public PhoneLine save(PhoneLine phoneLine) {
    return this.phoneLineRepository.save(phoneLine);
  }

  public PhoneLine findByNumber(String number) throws PhoneLineNotExists {
    return Optional.ofNullable(phoneLineRepository.findByNumber(number))
        .orElseThrow(PhoneLineNotExists::new);
  }

  public void deleteById(Integer id){
    this.phoneLineRepository.deleteById(id);
  }

  public void suspendPhoneLine(Integer id){
    this.phoneLineRepository.findById(id);
  }
}
