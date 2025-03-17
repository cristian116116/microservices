package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll().stream()
				.map(value -> new ClientDto(value.getId(), value.getDni(), value.getName(), value.getPassword(),
						value.getGender(), value.getAge(), value.getAddress(), value.getPhone(), value.isActive()))
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		return clientRepository.findById(id)
				.map(value -> new ClientDto(value.getId(), value.getDni(), value.getName(), value.getPassword(),
						value.getGender(), value.getAge(), value.getAddress(), value.getPhone(), value.isActive()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = Client.builder().dni(clientDto.getDni()).name(clientDto.getName())
				.password(clientDto.getPassword())
				.gender(clientDto.getGender()).age(clientDto.getAge()).address(clientDto.getAddress())
				.phone(clientDto.getPhone()).isActive(clientDto.isActive()).password(clientDto.getPassword()).build();
		Client clientCreated = clientRepository.save(client);
		clientDto.setId(clientCreated.getId());
		return clientDto;
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		if (getById(clientDto.getId()) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
		}
		Client client = Client.builder().dni(clientDto.getDni()).name(clientDto.getName())
				.password(clientDto.getPassword())
				.gender(clientDto.getGender()).age(clientDto.getAge()).address(clientDto.getAddress())
				.phone(clientDto.getPhone()).isActive(clientDto.isActive()).password(clientDto.getPassword()).build();
		clientRepository.save(client);
		return clientDto;
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		ClientDto clientDto = getById(id);
		if (clientDto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
		}
		clientDto.setActive(partialClientDto.isActive());
		Client client = Client.builder()
				.dni(clientDto.getDni())
				.name(clientDto.getName())
				.password(clientDto.getPassword())
				.gender(clientDto.getGender())
				.age(clientDto.getAge())
				.address(clientDto.getAddress())
				.phone(clientDto.getPhone())
				.isActive(clientDto.isActive())
				.build();
		clientRepository.save(client);
		return clientDto;
	}

	@Override
	public void deleteById(Long id) {
		if (!clientRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
		}
		clientRepository.deleteById(id);
	}

}
