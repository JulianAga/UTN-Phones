package com.utn.phones.model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "calls")
public class Call{


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Time duration;

  private LocalDate date;

  private Float costPrice;

  private Float totalPrice;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_origin_line")
  private PhoneLine originLine;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_destiny_line")
  private PhoneLine destinyLine;

  @Column(name = "origin_phone_line")
  private String originNumber;

  @Column(name = "destiny_phone_line")
  private String destinyNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "origin_city")
  private City originCity;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "destiny_city")
  private City destinyCity;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bill")
  private Bill bill;
}
