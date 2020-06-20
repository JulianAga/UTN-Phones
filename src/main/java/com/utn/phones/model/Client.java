package com.utn.phones.model;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString
@DiscriminatorValue(value = "1")
public class Client extends User {

  // @NotNull
  @OneToMany(mappedBy = "client")
  private List<PhoneLine> phoneLines;

  public Client(Integer id, @NotNull String DNI,
      @NotNull String username,
      @NotNull String password,
      @NotNull String name,
      @NotNull String surname, City city,
      UserType userType, Boolean active, List<PhoneLine> phoneLines) {
    super(id, DNI, username, password, name, surname, city, userType, active);
    this.phoneLines = phoneLines;
  }
}
