package com.devsu.hackerearth.backend.client.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping
	public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto clientDto) {
		ClientDto newClientDto = clientService.create(clientDto);
		URI location = URI.create("/api/clients/" + newClientDto.getId());
		return ResponseEntity.created(location).body(newClientDto);
	}

	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		return ResponseEntity.ok(clientService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(clientService.getById(id));
	}

	@PutMapping
	public ResponseEntity<ClientDto> update(@Valid @RequestBody ClientDto clientDto) {
		clientService.update(clientDto);
		return ResponseEntity.ok(clientDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@Valid @RequestBody PartialClientDto partialClientDto) {
		ClientDto clientDto = clientService.partialUpdate(id, partialClientDto);
		return ResponseEntity.ok(clientDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
