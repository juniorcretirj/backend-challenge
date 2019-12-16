package com.invillia.acme.services;

import com.invillia.acme.domain.entity.Order;
import com.invillia.acme.domain.entity.OrderItem;
import com.invillia.acme.domain.entity.StatusOrder;
import com.invillia.acme.domain.entity.Store;
import com.invillia.acme.exceptions.NotFoundException;
import com.invillia.acme.domain.inputs.CreateOrder;
import com.invillia.acme.domain.returned.OrderReturn;
import com.invillia.acme.repositories.OrderItemRepository;
import com.invillia.acme.repositories.OrderRepository;
import com.invillia.acme.repositories.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
	@Autowired
    StoreRepository storeRepository;
	@Autowired
    OrderRepository orderRepository;
	@Autowired
    OrderItemRepository orderItemRepository;

    
    @Transactional
    public UUID create(CreateOrder createOrder) {
        Store store = storeRepository.find(createOrder.getStoreId()).orElseThrow(() ->new NotFoundException(createOrder.getStoreId().toString()));

        Order order = new Order();
        order.setStore(store);
        order.setAddress(createOrder.getAddress());
        order.setStatus(StatusOrder.PAYMENT_PENDING.getName());
        orderRepository.create(order);

        createOrder.getItems().forEach(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setDescription(item.getDescription());
            orderItem.setPrice(item.getPrice());

            orderItemRepository.create(orderItem);
        });

        return order.getId();
    }

    public List<OrderReturn> list() {
        return orderRepository.list().stream().map(o -> new OrderReturn(o.getId(), o.getStore().getId(), o.getAddress(), o.getStatus().toString())).collect(Collectors.toList());
    }

    public OrderReturn find(UUID id) {
        Order order = orderRepository.find(id).orElseThrow(()-> new NotFoundException(id.toString()));

        return new OrderReturn(order.getId(), order.getStore().getId(), order.getAddress(), order.getStatus().toString());
    }
}
