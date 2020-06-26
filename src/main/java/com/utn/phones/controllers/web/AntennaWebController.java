package com.utn.phones.controllers.web;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.CallController;
import com.utn.phones.dto.CallRequestDto;
import com.utn.phones.restUtils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("/antenna")
public class AntennaWebController {

  private CallController callController;

  private SessionManager sessionManager;

  @Autowired
  public AntennaWebController(CallController callController, SessionManager sessionManager) {
    this.callController = callController;
    this.sessionManager = sessionManager;
  }

  //Agregado de llamadas
  @PostMapping("/call")
  public ResponseEntity<?> saveCall( @RequestHeader("Authorization") String token, @RequestBody CallRequestDto callRequestDto) {
    return ResponseEntity
        .created(RestUtils.getCallLocation(this.callController.save(callRequestDto))).build();
  }
}
