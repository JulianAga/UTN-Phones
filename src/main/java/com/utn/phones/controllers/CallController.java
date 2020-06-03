package com.utn.phones.controllers;

import com.utn.phones.model.Call;
import com.utn.phones.services.CallService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("")
  public List<Call> findAll() {
    return callService.findAll();
  }
}
