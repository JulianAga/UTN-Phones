package com.utn.phones.services;

import com.utn.phones.dto.UserRequestDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.model.Employee;
import com.utn.phones.repositories.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private EmployeeRepository employeeRepository;
  private CityService cityService;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository, CityService cityService) {
    this.employeeRepository = employeeRepository;
    this.cityService = cityService;
  }


  public List<Employee> findAll() {
    return this.employeeRepository.findAll();
  }
}
