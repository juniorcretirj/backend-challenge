package com.invillia.acme.domain.inputs;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateStore {
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
}
