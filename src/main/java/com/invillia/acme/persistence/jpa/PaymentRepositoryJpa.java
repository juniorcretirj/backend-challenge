package com.invillia.acme.persistence.jpa;

import com.invillia.acme.domain.entity.Payment;
import com.invillia.acme.repositories.PaymentRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryJpa implements PaymentRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void create(Payment payment) {
        entityManager.persist(payment);
        entityManager.flush();
    }

    @Override
    public Optional<Payment> find(UUID id) {
        return Optional.ofNullable(entityManager.find(Payment.class, id));
    }

    @Override
    public void update(Payment payment) {
        entityManager.merge(payment);
        entityManager.flush();
    }
}
