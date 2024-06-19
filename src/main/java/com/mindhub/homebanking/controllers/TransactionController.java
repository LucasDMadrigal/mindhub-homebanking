package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CreateTransactionDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

//    @Autowired
//    private TransactionRepository transactionRepository;

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;
    @GetMapping("/")
    public ResponseEntity<?> getTransactions() {
        return new ResponseEntity<>("No se encontraron cuentas", HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("current/create")
    public ResponseEntity<?> createTransactions(Authentication authentication, @RequestBody CreateTransactionDTO createTransactionDTO) {
        Client client = clientService.getClientByEmail(authentication.getName());

        Optional<Account> sourceAccountOptional = accountService.getAccountByNumber(createTransactionDTO.sourceAccount());

        if (!sourceAccountOptional.isPresent()) {
            return new ResponseEntity<>("Source account not found", HttpStatus.NOT_FOUND);
        }
        Account sourceAccount = sourceAccountOptional.get();

        Optional<Account> destinationAccountOptional = accountService.getAccountByNumber(createTransactionDTO.destinationAccount());
        if (!destinationAccountOptional.isPresent()) {
            return new ResponseEntity<>("Destination account not found", HttpStatus.NOT_FOUND);
        }
        Account destinationAccount = destinationAccountOptional.get();
        double destinationAccountBalance = destinationAccount.getBalance();
        double sourceAccountBalance = sourceAccount.getBalance();
        double transactionAmount = createTransactionDTO.amount();
        String description = createTransactionDTO.description();
        LocalDateTime date = LocalDateTime.now();
        TransactionType transactionType;

        if (!sourceAccount.getClient().equals(client)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


        if (transactionAmount <= 0) {
            return new ResponseEntity<>("El monto debe ser mayor a cero", HttpStatus.BAD_REQUEST);
        }

        if (sourceAccountBalance < transactionAmount) {
            return new ResponseEntity<>("Fondos insuficientes", HttpStatus.BAD_REQUEST);
        }

        if (sourceAccount.getNumber().equals(destinationAccount.getNumber())) {
            return new ResponseEntity<>("las cuentas de origen y destino no pueden ser la misma", HttpStatus.BAD_REQUEST);
        }


        if (description == null || description.trim().isEmpty()) {
            return new ResponseEntity<>("La descripción no puede estar vacia", HttpStatus.BAD_REQUEST);
        }

        sourceAccountBalance = sourceAccountBalance - transactionAmount;
        sourceAccount.setBalance(sourceAccountBalance);
        destinationAccountBalance = destinationAccountBalance + transactionAmount;
        destinationAccount.setBalance(destinationAccountBalance);

        accountService.saveAccount(sourceAccount);
        accountService.saveAccount(destinationAccount);

        Transaction newTransactionDebit = new Transaction(TransactionType.DEBIT, transactionAmount, description, date, sourceAccount);
        Transaction newTransactionCredit = new Transaction(TransactionType.CREDIT, transactionAmount, description, date, destinationAccount);

        transactionService.saveTransaction(newTransactionDebit);
        transactionService.saveTransaction(newTransactionCredit);

        return new ResponseEntity<>("Transaction created", HttpStatus.CREATED);
    }
}
