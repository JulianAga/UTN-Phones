package com.utn.phones.controllers.web;

import com.utn.phones.controllers.CallController;
import com.utn.phones.dto.CallRequestDto;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/antenna")
public class AntennaWebController {

  private CallController callController;

  @Autowired
  public AntennaWebController(CallController callController) {
    this.callController = callController;
  }

  @PostMapping("/call")
  public ResponseEntity<URI> saveCall(CallRequestDto callRequestDto) {
    return ResponseEntity.created(this.callController.save(callRequestDto)).build();
  }
}
