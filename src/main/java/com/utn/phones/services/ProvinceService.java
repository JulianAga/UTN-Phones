package com.utn.phones.services;

import com.utn.phones.model.Province;
import com.utn.phones.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    private ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(final ProvinceRepository provinceRepository){
        this.provinceRepository = provinceRepository;
    }
    public List<Province> findAll(){
        return this.provinceRepository.findAll();
    }

    public Province save(Province province){
        return this.provinceRepository.save(province);
    }
}
