package com.utn.phones.services;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.model.Call;
import com.utn.phones.projections.MostCalled;
import com.utn.phones.repositories.CallRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CallService {

  private CallRepository callRepository;

  @Autowired
  public CallService(CallRepository callRepository) {
    this.callRepository = callRepository;
  }

  public List<Call> findAll() {
    return callRepository.findAll();
  }

  public List<MostCalled> findMostCalledCities(Integer id) {
    return callRepository.findMostCalledCities(id);
  }

  public List<Call> findBetweenDates(Integer userId, BetweenDatesDto callBetweenDatesDto) {
    if (callBetweenDatesDto.getEnd().isBefore(callBetweenDatesDto.getStart())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The start date must be before the end date");
    } else {
      return callRepository
          .findAllByOriginLineClientIdAndDateBetween(userId, callBetweenDatesDto.getStart(),
              callBetweenDatesDto.getEnd());
    }
  }

}