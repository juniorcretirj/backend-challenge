package com.invillia.acme.service;

import com.invillia.acme.domain.entity.StatusOrder;
import com.invillia.acme.domain.inputs.*;
import com.invillia.acme.domain.returned.OrderReturn;
import com.invillia.acme.exceptions.PaymentPendingException;
import com.invillia.acme.exceptions.PaymentRequiredException;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.PaymentService;
import com.invillia.acme.services.StoreService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    private UUID storeId;
    private UUID orderId;

    private final String AMEX = "9625481265489632";
    private final String ADDRESS = "5454542333 NE 2nd Ave, Miami, FL 33132, Estados Unidos";
    private final String DESCRIPTION = "Zombie Strike";
    private final BigDecimal PRICE = new BigDecimal(10);

    @Before
    public void setup() {
        CreateStore createStore = new CreateStore();
        createStore.setName("Burger King");
        createStore.setAddress("Huston NI AVE, UE");

        storeId = storeService.create(createStore);

        CreateOrder orderCommand = new CreateOrder();
        orderCommand.setAddress(ADDRESS);
        orderCommand.setStoreId(storeId);
        orderCommand.setItems(new ArrayList<>());

        OrderItem item = new OrderItem();
        item.setDescription(DESCRIPTION);
        item.setPrice(PRICE);

        orderCommand.getItems().add(item);

        orderId = orderService.create(orderCommand);
    }

    @Test(expected = PaymentRequiredException.class)
    public void testRefundOrderPaymentRequired() {
        CreateOrderRefund command = new CreateOrderRefund();
        command.setOrderId(orderId);

        paymentService.refund(command);
    }

    @Test(expected = PaymentPendingException.class)
    public void testReOrderPending() {
        CreatePayment command = new CreatePayment();
        command.setOrderId(orderId);
        command.setCreditCardNumber(AMEX);
        paymentService.create(command);

        CreateOrderRefund refundCommand = new CreateOrderRefund();
        refundCommand.setOrderId(orderId);

        paymentService.refund(refundCommand);
    }

    @Test
    public void testRefundFinish() {
        CreatePayment command = new CreatePayment();
        command.setOrderId(orderId);
        command.setCreditCardNumber(AMEX);
        UUID id = paymentService.create(command);

        paymentService.complete(id);

        CreateOrderRefund refundCommand = new CreateOrderRefund();
        refundCommand.setOrderId(orderId);

        paymentService.refund(refundCommand);

        OrderReturn order = orderService.find(orderId);

        Assert.assertEquals(order.getStatus(), StatusOrder.REFUNDED.getName());
    }
}
