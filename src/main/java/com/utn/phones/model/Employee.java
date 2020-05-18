package com.utn.phones.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@DiscriminatorValue(value = "2")
public class Employee extends User {

}
