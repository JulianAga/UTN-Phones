package com.utn.phones.services;

import com.utn.phones.dto.OriginCityAndDestinyCityDto;
import com.utn.phones.exceptions.cityExceptions.CityNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Employee;
import com.utn.phones.model.Tariff;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.CityRepository;
import com.utn.phones.repositories.EmployeeRepository;
import com.utn.phones.repositories.TariffRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    EmployeeService employeeService;
    EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository, null);
    }

    @Test
    public void testFindAll() throws CityNotFoundException {
        Employee e1= new Employee(1, "123", "hola", "chau", "pepe", "perez", new City(), new UserType(), true);
        Employee e2= new Employee(2, "456", "hola", "chau", "pepe", "perez", new City(), new UserType(), true);

        List<Employee> employeeList = Arrays.asList(e1, e2);
        when(this.employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> employeeList2 = this.employeeService.findAll();

        Assert.assertEquals(employeeList.size(), employeeList2.size());
        Assert.assertEquals(employeeList.get(0).getId(), employeeList2.get(0).getId());
    }
}