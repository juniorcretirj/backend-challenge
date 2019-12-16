package com.invillia.acme.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.invillia.acme.domain.inputs.CreateOrder;
import com.invillia.acme.domain.inputs.CreateOrderRefund;
import com.invillia.acme.domain.returned.OrderReturn;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.PaymentService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/orders")
@Api(value = "Orders", description = "Orders control")
public class OrderController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	PaymentService paymentService;
	@Autowired
	OrderService orderService;
	

	@GetMapping()
	public ResponseEntity<List<OrderReturn>> List() {
		return ResponseEntity.ok(orderService.list());
	}

	@PostMapping()
	public ResponseEntity<Void> Create(@RequestBody @Valid CreateOrder order) {
		UUID id = orderService.create(order);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id)
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@PostMapping(value = "orders", produces = "application/json")
	public ResponseEntity<Void> RefundOrder(@RequestBody @Valid CreateOrderRefund order) {
		UUID refundId = paymentService.refund(order);

		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(refundId)
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
