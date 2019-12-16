package com.invillia.acme.repositories;

import com.invillia.acme.domain.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface OrderRepository {
    void create(Order order);

    Optional<Order> find(UUID orderId);

    List<Order> list();

    void update(Order order);
}
