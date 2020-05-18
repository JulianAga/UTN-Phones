package com.utn.phones.model;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
}
