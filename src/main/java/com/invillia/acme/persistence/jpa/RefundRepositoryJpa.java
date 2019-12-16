package com.invillia.acme.persistence.jpa;

import com.invillia.acme.domain.entity.Refund;
import com.invillia.acme.repositories.RefundRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RefundRepositoryJpa implements RefundRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void create(Refund refund) {
        entityManager.persist(refund);
        entityManager.flush();
    }
}
