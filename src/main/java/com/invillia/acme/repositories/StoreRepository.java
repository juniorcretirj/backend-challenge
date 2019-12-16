package com.invillia.acme.repositories;

import com.invillia.acme.domain.entity.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface StoreRepository {
    void create(Store store);

    void update(Store store);

    List<Store> list(String name, String address);

    Optional<Store> find(UUID id);
}
