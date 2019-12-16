package com.invillia.acme.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.UUID;

@Entity
@Data
@Table(name = "store")
public class Store {

  
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
}
