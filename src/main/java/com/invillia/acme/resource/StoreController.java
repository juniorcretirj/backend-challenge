package com.invillia.acme.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.invillia.acme.domain.inputs.CreateStore;
import com.invillia.acme.domain.inputs.UpdateStore;
import com.invillia.acme.domain.returned.StoreReturn;
import com.invillia.acme.services.StoreService;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/stores")
@Api(value = "Stores", description = "Stores control")
public class StoreController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	StoreService storeService;

	@GetMapping()
	public ResponseEntity<List<StoreReturn>> List(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "address", required = false) String address) {
		return ResponseEntity.ok(storeService.list(name, address));
	}

	@GetMapping("/{id}")
	public ResponseEntity<StoreReturn> Find(@PathVariable("id") UUID id) {
		final StoreReturn store = storeService.find(id);
		if (store == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(store);
	}

	@PostMapping()
	public ResponseEntity<Void> Create(@RequestBody @Valid CreateStore store) {
		final UUID id = storeService.create(store);
		final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> Update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateStore command) {
		if (!command.getId().equals(id))
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		storeService.update(command);
		return ResponseEntity.noContent().build();
	}
}
