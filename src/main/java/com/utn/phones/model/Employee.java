package com.utn.phones.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@Entity
@ToString
@DiscriminatorValue(value = "2")
public class Employee extends User {

  public Employee(Integer id, @NotNull String DNI,
      @NotNull String username,
      @NotNull String password,
      @NotNull String name,
      @NotNull String surname, City city,
      UserType userType, Boolean active) {
    super(id, DNI, username, password, name, surname, city, userType, active);
  }
}
