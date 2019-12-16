package com.invillia.acme.repositories;

import com.invillia.acme.domain.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    void create(Payment payment);

    Optional<Payment> find(UUID id);

    void update(Payment payment);
}
