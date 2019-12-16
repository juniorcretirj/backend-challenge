package com.invillia.acme.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.invillia.acme.domain.entity.OrderItem;
import com.invillia.acme.repositories.OrderItemRepository;

@Repository
public class OrderItemRepositoryJpa implements OrderItemRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<OrderItem> find(UUID id) {
        return Optional.ofNullable(entityManager.find(OrderItem.class, id));
    }

    @Override
    public void create(OrderItem orderItem) {
        entityManager.persist(orderItem);
        entityManager.flush();
    }
}
