package com.invillia.acme.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.invillia.acme.domain.entity.Order;
import com.invillia.acme.repositories.OrderRepository;

@Repository
public class OrderRepositoryJpa implements OrderRepository {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void create(Order order) {
		entityManager.persist(order);
		entityManager.flush();
	}

	@Override
	public Optional<Order> find(UUID id) {
		return Optional.ofNullable(entityManager.find(Order.class, id));
	}

	@Override
	public List<Order> list() {
		
		return entityManager
				.createQuery("select p from Order as p left join  p.payment inner join  p.store ", Order.class)
				.getResultList();

	}

	@Override
	public void update(Order order) {
		entityManager.merge(order);
		entityManager.flush();
	}
}
