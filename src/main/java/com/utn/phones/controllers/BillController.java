package com.utn.phones.controllers;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.model.Bill;
import com.utn.phones.services.BillService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {

  private BillService billService;

  @Autowired
  public BillController(BillService billService) {
    this.billService = billService;
  }

  public List<Bill> findAll() {
    return this.billService.findAll();
  }

  public ResponseEntity<List<Bill>> findBetweenDates(Integer id,
      @RequestBody BetweenDatesDto betweenDatesDto) {
    return ResponseEntity.ok(this.billService.findBetweenDates(id, betweenDatesDto));
  }
}

