package com.utn.phones.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

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
public class Bill  implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "phone_line")
  private PhoneLine phoneLine;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  private Integer quantityOfCalls;

  private Float costPrice;

  private Float totalPrice;

  private LocalDateTime date;

  private LocalDateTime expiringDate;

}
