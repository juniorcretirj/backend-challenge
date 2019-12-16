package com.invillia.acme.repositories;

import com.invillia.acme.domain.entity.OrderItem;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository {
    Optional<OrderItem> find(UUID id);

    void create(OrderItem orderItem);
}
