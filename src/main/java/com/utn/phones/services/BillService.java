package com.utn.phones.services;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.model.Bill;
import com.utn.phones.repositories.BillRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BillService {

  private BillRepository billRepository;

  @Autowired
  public BillService(final BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  public List<Bill> findAll() {
    return this.billRepository.findAll();
  }

  public List<Bill> findBetweenDates(Integer userId, BetweenDatesDto betweenDatesDto) {
    if (betweenDatesDto.getEnd().isBefore(betweenDatesDto.getStart())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The start date must be before the end date");
    } else {
      return billRepository.findAllByClientIdAndDateBetween(userId, betweenDatesDto.getStart(),
          betweenDatesDto.getEnd());
    }
  }
}
