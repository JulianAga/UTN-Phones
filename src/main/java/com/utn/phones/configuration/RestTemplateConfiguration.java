package com.utn.phones.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfiguration {

  @Bean("restTemplate")
  RestTemplate createRestTemplate() {
    return new RestTemplate();
  }

}
