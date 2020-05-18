package com.utn.phones.services;

import com.utn.phones.model.City;
import com.utn.phones.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.server.ResponseStatusException;

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

    public City findById(Integer id){
       return cityRepository.findById(id).orElseThrow(() -> new
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct city Id"));
    }
}
