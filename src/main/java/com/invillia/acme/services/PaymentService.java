package com.invillia.acme.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.invillia.acme.domain.entity.Order;
import com.invillia.acme.domain.entity.Payment;
import com.invillia.acme.domain.entity.Refund;
import com.invillia.acme.domain.entity.StatusOrder;
import com.invillia.acme.domain.entity.StatusPayment;
import com.invillia.acme.exceptions.NotFoundException;
import com.invillia.acme.exceptions.OrderCanceledException;
import com.invillia.acme.exceptions.PaymentPendingException;
import com.invillia.acme.exceptions.PaymentRequiredException;
import com.invillia.acme.exceptions.RefundExpiredException;
import com.invillia.acme.domain.inputs.CreateOrderRefund;
import com.invillia.acme.domain.inputs.CreatePayment;
import com.invillia.acme.repositories.OrderItemRepository;
import com.invillia.acme.repositories.OrderRepository;
import com.invillia.acme.repositories.PaymentRepository;
import com.invillia.acme.repositories.RefundRepository;

@Service
public class PaymentService {
	private final int EXPIRED = 10;

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	RefundRepository refundRepository;

	@Transactional
	public UUID create(CreatePayment command) {
		Order order = orderRepository.find(command.getOrderId()).orElseThrow(() ->new NotFoundException(command.getOrderId().toString()));
		Payment payment = new Payment();
		payment.setCreditCardNumber(command.getCreditCardNumber());
		payment.setPaymentDate(LocalDateTime.now());
		payment.setStatus(StatusPayment.PENDING.getName());
		payment.setOrder(order);
		paymentRepository.create(payment);
		order.setPayment(payment);
		order.setStatus(StatusOrder.PAYMENT_ACCEPT.getName());
		orderRepository.update(order);
		return payment.getId();
	}

	@Transactional
	public UUID refund(CreateOrderRefund command) {
		Order order = orderRepository.find(command.getOrderId()).orElseThrow(() ->new NotFoundException(command.getOrderId().toString()));
		testRefundOrder(order);
		Payment payment = Optional.ofNullable(order.getPayment()).orElseThrow(PaymentRequiredException::new);
		testPaymentToRefund(payment);
		Refund refund = new Refund();
		refund.setOrder(order);
		refundRepository.create(refund);
		order.setStatus(StatusOrder.REFUNDED.getName());
		orderRepository.update(order);
		return refund.getId();
	}

	

	@Transactional
	public void complete(UUID id) {
		Payment payment = paymentRepository.find(id).orElseThrow(() ->new NotFoundException(id.toString()));
		payment.setStatus(StatusPayment.COMPLETED.getName());
		paymentRepository.update(payment);
		Order order = orderRepository.find(payment.getOrder().getId()).orElseThrow(() ->new NotFoundException(payment.getOrder().getId().toString()));
		order.setStatus(StatusOrder.TRANSPORT.getName());
		order.setConfirmationDate(LocalDateTime.now());
		orderRepository.update(order);
	}

	private void testRefundOrder(Order order) {
			
		if (order.getStatus().equals(StatusOrder.PAYMENT_PENDING.getName()))
			throw new PaymentRequiredException();
		if (order.getStatus().equals(StatusOrder.PAYMENT_ACCEPT.getName()))
			throw new PaymentPendingException();
		if (order.getConfirmationDate().plusDays(EXPIRED).isBefore(LocalDateTime.now()))
	            throw new RefundExpiredException(EXPIRED);

	}

	private void testPaymentToRefund(Payment payment) {
		if (!payment.getStatus().equals(StatusPayment.COMPLETED.getName()))
			throw new PaymentPendingException();
	}
}
