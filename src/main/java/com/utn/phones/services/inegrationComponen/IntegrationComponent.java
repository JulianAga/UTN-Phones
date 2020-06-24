package com.utn.phones.services.inegrationComponen;

import com.utn.phones.model.Client;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class IntegrationComponent {

  private RestTemplate rest;
  private static String url = "http://localhost:8080/client/1";

  @PostConstruct
  private void init() {
    rest = new RestTemplateBuilder()
        .build();
  }

  public Client getPetsFromApi() {
    return rest.getForObject(url, Client.class);
  }

}
