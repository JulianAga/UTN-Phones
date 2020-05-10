package com.utn.phones.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "user_types")
public class UserType  implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private Integer id;

  @NotNull
  private String type;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userType")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<User> users;

}
