package com.utn.phones.services;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.exceptions.billExceptions.InvalidDateException;
import com.utn.phones.model.Bill;
import com.utn.phones.repositories.BillRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

  private BillRepository billRepository;

  @Autowired
  public BillService(final BillRepository billRepository) {
    this.billRepository = billRepository;
  }

  public List<Bill> findBetweenDates(Integer userId, BetweenDatesDto betweenDatesDto)
      throws InvalidDateException {
    if (betweenDatesDto.getEnd().isBefore(betweenDatesDto.getStart())) {
      throw new InvalidDateException();
    } else {
      return billRepository.findAllByClientIdAndDateBetween(userId, betweenDatesDto.getStart(),
          betweenDatesDto.getEnd());
    }
  }
}
