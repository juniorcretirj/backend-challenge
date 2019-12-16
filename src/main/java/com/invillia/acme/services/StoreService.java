package com.invillia.acme.services;

import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.exceptions.NotFoundException;
import com.invillia.acme.domain.inputs.CreateStore;
import com.invillia.acme.domain.inputs.UpdateStore;
import com.invillia.acme.domain.returned.StoreReturn;
import com.invillia.acme.repositories.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public UUID create(CreateStore command) {
        final Store store = new Store();
        store.setName(command.getName());
        store.setAddress(command.getAddress());

        storeRepository.create(store);

        return store.getId();
    }

    @Transactional
    public void update(UpdateStore command) {
        final Store store = storeRepository.find(command.getId()).orElseThrow(() ->new NotFoundException(command.getId().toString()));
        store.setName(command.getName());
        store.setAddress(command.getAddress());

        storeRepository.update(store);
    }

    public List<StoreReturn> list(String name, String address) {
    	
        return storeRepository.list(name, address).stream().map(s -> new StoreReturn(s.getId(), s.getName(), s.getAddress())).collect(Collectors.toList());
    }

    public StoreReturn find(UUID id) {
        final Store store = storeRepository.find(id).orElseThrow(()-> new NotFoundException(id.toString()));

        return new StoreReturn(store.getId(), store.getName(), store.getAddress());
    }
}
