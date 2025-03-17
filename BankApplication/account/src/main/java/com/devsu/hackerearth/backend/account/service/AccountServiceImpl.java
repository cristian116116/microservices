package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // aca hubiera colocado un Mapper mejor
    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(AccountDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public AccountDto create(AccountDto accountDto) {

        Account account = Account.builder()
                .number(accountDto.getNumber())
                .type(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .clientId(accountDto.getClientId())
                .build();

        accountRepository.save(account);
        return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account account = Account.builder()
                .number(accountDto.getNumber())
                .type(accountDto.getType())
                .initialAmount(accountDto.getInitialAmount())
                .isActive(accountDto.isActive())
                .clientId(accountDto.getClientId())
                .build();

        accountRepository.save(account);
        return accountDto;
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        account.setActive(partialAccountDto.isActive());
        accountRepository.save(account);
        return AccountDto.fromEntity(account);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> getAccountsByClientId(Long clientId) {
        List<Account> accounts = accountRepository.findByClientId(clientId);

        if (accounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No accounts found for clientId: " + clientId);
        }
        return accounts.stream().map(AccountDto::fromEntity).collect(Collectors.toList());

    }

}
