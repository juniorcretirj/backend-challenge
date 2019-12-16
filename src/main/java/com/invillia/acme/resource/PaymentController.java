package com.invillia.acme.resource;
import com.invillia.acme.domain.inputs.CreatePayment;
import com.invillia.acme.services.PaymentService;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.util.UUID;


@RestController
@RequestMapping("/payments")
@Api(value = "Payments", description = "Payments control")
public class PaymentController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
    PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Void> Create(@RequestBody @Valid CreatePayment payment) {
        UUID id = paymentService.create(payment);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> Complete(@PathVariable UUID id) {
        paymentService.complete(id);
        return ResponseEntity.noContent().build();
    }
}
