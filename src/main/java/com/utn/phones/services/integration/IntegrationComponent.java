package com.utn.phones.services.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class IntegrationComponent {

    private RestTemplate rest;

    private static String url = "http://localhost:8080/pets/";

    @PostConstruct
    private void init() {
        rest = new RestTemplateBuilder()
                .build();
    }

    public Pet getPetsFromApiId(Integer id) {
        return rest.getForObject(url + id, Pet.class);
    }

}