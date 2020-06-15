package com.utn.phones.configuration;

import com.utn.phones.Sessions.AntennaSessionFilter;
import com.utn.phones.Sessions.EmployeeSessionFilter;
import com.utn.phones.Sessions.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableScheduling
public class Config {

  @Autowired
  SessionFilter sessionFilter;

  @Autowired
  EmployeeSessionFilter employeeSessionFilter;

  @Autowired
  AntennaSessionFilter antennaSessionFilter;

  @Bean
  public FilterRegistrationBean sessionExistsFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(sessionFilter);
    registration.addUrlPatterns("/api/*");
    return registration;
  }

  @Bean
  public FilterRegistrationBean superUserSessionExistsFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(employeeSessionFilter);
    registration.addUrlPatterns("/back-office/*");
    return registration;
  }

  @Bean
  public FilterRegistrationBean antennaRequestFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(antennaSessionFilter);
    registration.addUrlPatterns("/antenna/*");
    return registration;
  }

}