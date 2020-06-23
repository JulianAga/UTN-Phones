package com.utn.phones.services;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.model.Bill;
import com.utn.phones.services.integration.IntegrationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class IntegrationService {

    @Autowired
    IntegrationComponent integrationComponent;

    public List<Bill> getBills(@RequestHeader("Authorization") String token,
                               @RequestBody BetweenDatesDto betweenDatesDto) {
        return integrationComponent.getBillFromApi(token, betweenDatesDto);
    }
}