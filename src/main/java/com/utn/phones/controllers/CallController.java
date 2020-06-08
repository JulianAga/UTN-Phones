package com.utn.phones.controllers;

import com.utn.phones.dto.BetweenDatesDto;
import com.utn.phones.dto.CallRequestDto;
import com.utn.phones.model.Call;
import com.utn.phones.projections.MostCalled;
import com.utn.phones.services.CallService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call")
public class CallController {

  private CallService callService;

  @Autowired
  public CallController(final CallService callService) {
    this.callService = callService;
  }

  @GetMapping(value = "/top/{id}")
  public ResponseEntity<List<MostCalled>> findMostCalledCities(@PathVariable Integer id) {
    return ResponseEntity.ok(callService.findMostCalledCities(id));
  }

  @GetMapping("/between/{id}")
  public ResponseEntity<List<Call>> findBetweenDates(
      @RequestBody BetweenDatesDto callBetweenDatesDto, @PathVariable Integer id) {
    return ResponseEntity.ok(this.callService.findBetweenDates(id, callBetweenDatesDto));
  }

  @GetMapping("/client/{id}")
  public ResponseEntity<List<Call>> findCallsFromClient(@PathVariable Integer id) {
    return ResponseEntity.ok(this.callService.findCallsFromClient(id));
  }

  @PostMapping("")
  public void save(CallRequestDto callRequestDto) {
    this.callService.saveDto(callRequestDto);
  }
}
