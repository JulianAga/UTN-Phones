package com.utn.phones.services;

import com.utn.phones.exceptions.NoGarciasLinesFoundException;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.repositories.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneLineService {

    private PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineService(final PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    public List<PhoneLine> findAll(){
        return this.phoneLineRepository.findAll();
    }

    public PhoneLine save(PhoneLine phoneLine){
        return this.phoneLineRepository.save(phoneLine);
    }

    public List<PhoneLine> getGarciasPhoneLines() throws NoGarciasLinesFoundException  {
        return this.phoneLineRepository.getGarciasPhoneLines(); }
}
