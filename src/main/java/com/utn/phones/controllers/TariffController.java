package com.utn.phones.controllers;

import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.model.Tariff;
import com.utn.phones.services.TariffService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tariff")
public class TariffController {

  TariffService tariffService;

  @Autowired
  public TariffController(TariffService tariffService) {
    this.tariffService = tariffService;
  }

  @GetMapping()
  public List<Tariff> findAll() {
    return this.tariffService.findAll();
  }

  @GetMapping("/city")
  public List<Tariff> findByOriginNameAndDestinyName(@RequestBody
      OriginCityAndDestinyCityDto originCityAndDestinyCityDto) {
    return this.tariffService
        .findByOriginNameAndDestinyName(originCityAndDestinyCityDto.getOriginCity(),
            originCityAndDestinyCityDto.getDestinyCity());
  }

}
