package com.utn.phones.controllers;

import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/")
    public PhoneLine save(@RequestBody PhoneLine phoneLine){
        return this.phoneLineService.save(phoneLine);
    }

    @GetMapping("/garcia")
    public List<PhoneLine> getGarciasPhoneLines(){
        return this.phoneLineService.getGarciasPhoneLines();
    }
}
