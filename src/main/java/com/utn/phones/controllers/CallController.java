package com.utn.phones.controllers;

import com.utn.phones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
