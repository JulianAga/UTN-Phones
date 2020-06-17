package com.utn.phones.controllers;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.model.Employee;
import com.utn.phones.services.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping("/")
  public Employee save(@RequestBody UserRequestDto client) throws CityNotFoundException {
    return this.employeeService.save(client);
  }

  @GetMapping("/")
  public List<Employee> findAll() {
    return this.employeeService.findAll();
  }


}
