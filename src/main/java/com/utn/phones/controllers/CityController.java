package com.utn.phones.controllers;

import com.utn.phones.model.City;
import com.utn.phones.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    CityService cityService;
//todo Eliminar city controller
    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/")
    public List<City> findAll() {
        return this.cityService.findAll();
    }
}
