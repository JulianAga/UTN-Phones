package com.utn.phones.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@ToString
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
public class User {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @NotNull
  private String DNI;

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  private String name;

  @NotNull
  private String surname;

//  @NotNull
  @ManyToOne
  @JoinColumn(name = "city")
  private City city;

//  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_type", insertable = false, updatable = false)
  private UserType userType;

}
