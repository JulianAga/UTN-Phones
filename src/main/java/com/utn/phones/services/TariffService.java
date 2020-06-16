package com.utn.phones.services;

import com.utn.phones.model.Tariff;
import com.utn.phones.repositories.TariffRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TariffService {

  private TariffRepository tariffRepository;

  @Autowired
  public TariffService(final TariffRepository tariffRepository) {
    this.tariffRepository = tariffRepository;
  }

  /***
   * Consulta de tarifas
   * @return List with tariffs
   */
  public List<Tariff> findAll() {
    return this.tariffRepository.findAll();
  }

  public List<Tariff> findByOriginNameAndDestinyName(String originName, String destinyName) {
    return this.tariffRepository.findByOriginCityNameAndDestinyCityName(originName, destinyName);
  }

}
