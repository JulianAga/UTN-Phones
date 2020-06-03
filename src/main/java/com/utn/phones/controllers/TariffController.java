package com.utn.phones.controllers;

import com.utn.phones.model.Tariff;
import com.utn.phones.services.TariffService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tariff")
public class TariffController {

  TariffService tariffService;

  @Autowired
  public TariffController(TariffService tariffService) {
    this.tariffService = tariffService;
  }

  public List<Tariff> findAll() {
    return this.tariffService.findAll();
  }

}
