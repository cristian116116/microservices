package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final ClientService clientService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService,
            ClientService clientService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        return transactionRepository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {

        AccountDto accountDto = Optional.of(accountService.getById(transactionDto.getAccountId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The transaction cannot be created because the account ID does not exist."));

        double newBalance = calculateNewBalance(accountDto, transactionDto);

        transactionDto.setBalance(newBalance);
        accountDto.setInitialAmount(newBalance);

        Transaction transaction = Transaction.builder().id(transactionDto.getId())
                .accountId(transactionDto.getAccountId()).amount(transactionDto.getAmount())
                .balance(transactionDto.getBalance()).date(transactionDto.getDate()).build();

        transactionRepository.save(transaction);
        accountService.create(accountDto);

        return transactionDto;
    }

    private double calculateNewBalance(AccountDto accountDto, TransactionDto transactionDto) {
        double currentBalance = accountDto.getInitialAmount();
        double amount = transactionDto.getAmount();
        String type = transactionDto.getType();

        if ("Withdrawal".equalsIgnoreCase(type)) {
            if (currentBalance < amount) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
            }
            return currentBalance - amount;
        }

        if ("Deposit".equalsIgnoreCase(type)) {
            return currentBalance + amount;
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de transacción inválido");
    }

    // Implemenaria paginacion y un limite de registros si el reporte es muy grande
    // + adicionalmente pondria un cache para el llamado de clientes
    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {

        List<BankStatementDto> bankStatementDto = new ArrayList<>();

        List<AccountDto> accountDto = accountService.getAccountsByClientId(clientId);

        ClientDto clientDto = clientService.getClientById(clientId);

        bankStatementDto = accountDto
                .stream()
                .map(account -> account)
                .flatMap(account -> transactionRepository.findByAccountIdAndDateRange(clientId,
                        dateTransactionStart, dateTransactionEnd)
                        .stream()
                        .map(transaction -> BankStatementDto.builder()
                                .date(transaction.getDate())
                                .client(clientDto.getName())
                                .accountNumber(account.getNumber())
                                .accountType(account.getType())
                                .initialAmount(account.getInitialAmount())
                                .isActive(account.isActive())
                                .transactionType(transaction.getType())
                                .amount(transaction.getAmount())
                                .balance(transaction.getBalance())
                                .build()))
                .collect(Collectors.toList());

        return bankStatementDto;
    }


}
