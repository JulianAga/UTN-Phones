package com.utn.phones.services.integration;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.model.Bill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class IntegrationComponent {

    private RestTemplate rest;

    private static String url = "http://localhost:8080/api/bill";

    @PostConstruct
    private void init() {
        rest = new RestTemplateBuilder()
                .build();
    }

    public List<Bill> getBillFromApi(@RequestHeader("Authorization") String token,
                                     @RequestBody BetweenDatesDto betweenDatesDto) {
        ResponseEntity<List<Bill>> response= rest.getForObject(url, ResponseEntity.class);
        return response.getBody();
    }

    //Deberia haber un token para apis, no el de login
    /*HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.set("Authorization", "Bearer "+accessToken);

HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
String result = restTemplate.postForObject(url, entity, String.class); */

    // igual lo voy a sacar de usercontroller y listo, sin autorizacion, sin requestbody, etc

}