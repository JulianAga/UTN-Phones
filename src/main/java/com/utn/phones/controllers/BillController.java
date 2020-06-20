package com.utn.phones.controllers;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.model.Bill;
import com.utn.phones.services.BillService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {

  private BillService billService;

  @Autowired
  public BillController(BillService billService) {
    this.billService = billService;
  }

  public List<Bill> findBetweenDates(Integer id,
      @RequestBody BetweenDatesDto betweenDatesDto) throws InvalidDateException {
    return this.billService.findBetweenDates(id, betweenDatesDto);
  }
}

