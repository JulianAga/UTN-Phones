package com.utn.phones.services;

import com.utn.phones.services.integration.IntegrationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

    @Autowired
    IntegrationComponent integrationComponent;

    public Pet getPet(Integer id) {
        return integrationComponent.getPetsFromApiId(id);
    }
}