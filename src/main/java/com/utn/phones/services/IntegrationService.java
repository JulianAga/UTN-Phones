package com.utn.phones.services;

import com.utn.phones.model.Client;
import com.utn.phones.services.inegrationComponen.IntegrationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

  @Autowired
  IntegrationComponent integrationComponent;

  public Client getPet() {
    return integrationComponent.getPetsFromApi();
  }
}