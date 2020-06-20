package com.utn.phones.controllers;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.CallRequestDto;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exceptions.callExceptions.CallNotFoundException;
import com.utn.phones.exceptions.dateExceptions.InvalidDateException;
import com.utn.phones.model.Call;
import com.utn.phones.services.CallService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/call")
public class CallController {

  private CallService callService;

  @Autowired
  public CallController(final CallService callService) {
    this.callService = callService;
  }

  public List<MostCalledDto> findMostCalledCities(@PathVariable Integer id)
      throws CallNotFoundException {
    return callService.findMostCalledCities(id);
  }

  public List<Call> findBetweenDates(BetweenDatesDto callBetweenDatesDto,
      Integer id) throws InvalidDateException {
    return this.callService.findBetweenDates(id, callBetweenDatesDto);
  }

  public List<Call> findCallsFromClient(@PathVariable Integer id) throws CallNotFoundException {
    return this.callService.findCallsFromClient(id);
  }

  public URI save(CallRequestDto callRequestDto) {
    return getLocation(this.callService.saveDto(callRequestDto));
  }


  private URI getLocation(Call call) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(call.getId())
        .toUri();
  }
}
