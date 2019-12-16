package com.invillia.acme.domain.inputs;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CreatePayment {
    @NotNull
    private UUID orderId;
    @NotEmpty
    private String creditCardNumber;
}
