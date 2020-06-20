package com.utn.phones.controllers;

import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.model.Tariff;
import com.utn.phones.services.TariffService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TariffController {

  private TariffService tariffService;

  @Autowired
  public TariffController(TariffService tariffService) {
    this.tariffService = tariffService;
  }

  public List<Tariff> findAll(
      OriginCityAndDestinyCityDto originCityAndDestinyCityDto) throws CityNotFoundException {
    return this.tariffService.findAll(originCityAndDestinyCityDto.getOriginCity(),
        originCityAndDestinyCityDto.getDestinyCity());
  }
}
