package com.utn.phones.services;

import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.repositories.CityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

  private CityRepository cityRepository;

  @Autowired
  public CityService(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  public List<City> findAll() {
    return this.cityRepository.findAll();
  }

  public City save(City city) {
    return this.cityRepository.save(city);
  }

  public City findById(Integer id) throws CityNotFoundException {
    return cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
  }
}
