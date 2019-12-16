package com.invillia.acme.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.invillia.acme.domain.entity.StatusOrder;
import com.invillia.acme.domain.inputs.CreateOrder;
import com.invillia.acme.domain.inputs.CreateStore;
import com.invillia.acme.domain.inputs.OrderItem;
import com.invillia.acme.domain.returned.OrderReturn;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.StoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderService orderService;

    private UUID storeId;
    private UUID id;

    private final String ADDRESS = "345 NE 2nd Ave, Miami, FL 33132, Estados Unidos";
    private final String DESCRIPTION = "Nerf";
    private final BigDecimal PRICE = new BigDecimal(99.90);

    @Before
    public void setup() {
        CreateStore createStore = new CreateStore();
        createStore.setName("ACME");
        createStore.setAddress("1290 NW 74th St, Miami, FL 33147, Estados Unidos");

        storeId = storeService.create(createStore);

        CreateOrder command = new CreateOrder();
        command.setAddress(ADDRESS);
        command.setStoreId(storeId);
        command.setItems(new ArrayList<>());

        OrderItem item = new OrderItem();
        item.setDescription(DESCRIPTION);
        item.setPrice(PRICE);

        command.getItems().add(item);

        id = orderService.create(command);
    }

    @Test
    public void testCreate() {
        OrderReturn order = orderService.find(id);

        Assert.assertEquals(order.getAddress(), ADDRESS);
        Assert.assertEquals(order.getStatus(), StatusOrder.PAYMENT_PENDING.getName());
    }

    @Test
    public void TestFind() {
        List<OrderReturn> orders = orderService.list();

        Assert.assertNotEquals(0, orders.size());
    }
}
