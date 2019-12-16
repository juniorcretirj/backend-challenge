package com.invillia.acme.domain.inputs;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CreateOrderRefund {
    @NotNull
    private UUID orderId;
}
