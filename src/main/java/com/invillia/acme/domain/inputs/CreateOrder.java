package com.invillia.acme.domain.inputs;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class CreateOrder {
    @NotEmpty
    private String address;
    @NotNull
    private UUID storeId;
    @NotNull
    private List<OrderItem> items;
}
