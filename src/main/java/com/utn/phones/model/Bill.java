package com.utn.phones.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_line")
    private PhoneLine phoneLine;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "quantity_of_calls")
    private Integer quantityOfCalls;

    @Column(name = "cost_price")
    private Float costPrice;

    @Column(name = "total_price")
    private Float totalPrice;

    private LocalDateTime date;

    @Column(name = "expiring_date")
    private LocalDateTime expiringDate;

}
