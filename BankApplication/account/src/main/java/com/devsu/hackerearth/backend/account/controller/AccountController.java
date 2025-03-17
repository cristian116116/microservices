package com.devsu.hackerearth.backend.account.controller;

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

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.getById(id));
	}

	@PostMapping
	public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) {
		URI location = URI.create("/api/accounts/" + accountDto.getId());
		accountService.create(accountDto);
		return ResponseEntity.created(location).body(accountDto);
	}

	@PutMapping
	public ResponseEntity<AccountDto> update(@Valid @RequestBody AccountDto accountDto) {
		accountService.update(accountDto);
		return ResponseEntity.ok(accountDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@Valid @RequestBody PartialAccountDto partialAccountDto) {
		AccountDto accountDto = accountService.partialUpdate(id, partialAccountDto);
		return ResponseEntity.ok(accountDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		accountService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
