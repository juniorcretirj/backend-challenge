package com.invillia.acme.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.repositories.StoreRepository;

@Repository
public class StoreRepositoryJpa implements StoreRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void create(Store store) {
        entityManager.persist(store);
        entityManager.flush();
    }

    @Override
    public void update(Store store) {
        entityManager.merge(store);
        entityManager.flush();
    }

    @Override
    public List<Store> list(String name, String address) {
    	    
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Store> cq = cb.createQuery(Store.class);
        Root<Store> root = cq.from(Store.class);
        Predicate namePredicate = cb.equal(root.get("name"), name);
        Predicate addressPredicate = cb.equal(root.get("address"), address);
        Predicate finalPredicate =cb.or(namePredicate,addressPredicate);
        cq.where(finalPredicate);
        TypedQuery<Store> query = entityManager.createQuery(cq);
        return query.getResultList();

       
    }

    @Override
    public Optional<Store> find(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(Store.class, id));
    }
}