package com.utn.phones.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "bills")
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "phone_line")
  private PhoneLine phoneLine;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client")
  private Client client;

  @Column(name = "quantity_of_calls")
  private Integer quantityOfCalls;

  @Column(name = "cost_price")
  private Float costPrice;

  @Column(name = "total_price")
  private Float totalPrice;

  private LocalDate date;

  @Column(name = "expiring_date")
  private LocalDate expiringDate;

}
