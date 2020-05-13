package com.utn.phones.controllers;

import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phoneline")
public class PhoneLineController {
    PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @GetMapping("/")
    public List<PhoneLine> findAll() {
        return this.phoneLineService.findAll();
    }
}
