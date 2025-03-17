package com.devsu.hackerearth.backend.account.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;

@Service
public class ClientService {

    private final RestTemplate restTemplate;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClientDto getClientById(Long id) {
        String url = "http://localhost:9090/api/clients/{id}";
        try {
            return restTemplate.getForObject(url, ClientDto.class, id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), "Error en la API: " + e.getMessage());
        }
    }
}