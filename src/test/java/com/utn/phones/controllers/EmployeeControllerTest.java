package com.utn.phones.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.utn.phones.model.City;
import com.utn.phones.model.Employee;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.EmployeeRepository;
import com.utn.phones.services.EmployeeService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class EmployeeControllerTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private EmployeeService employeeService;

  private EmployeeController employeeController;

  @Before
  public void setUp() {
    initMocks(this);
    this.employeeController = new EmployeeController(employeeService);
  }

  @Test
  public void testFindAll() {

    Employee employee = new Employee(1, "dwad", "sad", "ad", "dad", "sad", new City(),
        new UserType(), true);
    Employee employee2 = new Employee(1, "carlos", "asd", "asd", "sad", "sad", new City(),
        new UserType(), true);

    List<Employee> employeeList = Arrays.asList(employee, employee2);
    when(this.employeeService.findAll()).thenReturn(employeeList);

    List<Employee> employeeTest = this.employeeController.findAll();

    Assert.assertEquals(employeeList.size(), employeeTest.size());
    Assert.assertEquals(employeeList.get(0).getId(), employeeTest.get(0).getId());
  }

}
