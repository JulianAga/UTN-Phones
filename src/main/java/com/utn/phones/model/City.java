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
@Table(name = "cities")
public class City implements Serializable{

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @NotNull
  private Integer prefix;

  @NotNull
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "province_id")
  private Province province;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "city")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<User> users;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "originCity")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<Call> originCalls;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "destinyCity")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<Call> destinyCalls;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "originCity")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<Tariff> originCity;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "destinyCity")
//  @ToString.Exclude
//  @JsonBackReference
//  private List<Tariff> destinyCity;
}
