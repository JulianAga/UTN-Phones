package com.utn.phones.services;

import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
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
  public List<Tariff> findAll(String originName, String destinyName) throws CityNotFoundException {
    if (originName != null && destinyName != null) {
      try {
        return this.tariffRepository
            .findByOriginCityNameAndDestinyCityName(originName, destinyName);
      }catch (Exception c){
        throw new CityNotFoundException();
      }
    }
    return this.tariffRepository.findAll();
  }

}
