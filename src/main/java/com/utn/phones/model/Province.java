package com.utn.phones.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "provinces")
public class Province implements Serializable{

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @NotNull
  @JsonProperty(value = "name")
  private String name;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "province")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<User> users;
//
//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "province")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<City> cities;

}
