package com.invillia.acme.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

 
    private String status;

    private String creditCardNumber;

    @OneToOne
    private Order order;

    private LocalDateTime paymentDate;
}
