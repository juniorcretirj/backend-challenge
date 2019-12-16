package com.invillia.acme.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "\"order\"")
public class Order {

    public Order(UUID id2, UUID id3, String address, String status) {
    	 this.id = id2;
         this.store.setId(id3);
         this.address = address;
         this.status = status;
		
	}

	public Order() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Store store;

    private String address;

    private LocalDateTime confirmationDate;


    private String status;

    @OneToOne
    private Payment payment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> items;
}
