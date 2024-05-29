package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CreateTransactionDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public ResponseEntity<?> getTransactions() {
        return new ResponseEntity<>("No se encontraron cuentas", HttpStatus.NOT_FOUND);
    }

    @Transactional
    @GetMapping("current/create")
    public ResponseEntity<?> createTransactions(Authentication authentication, @RequestBody CreateTransactionDTO createTransactionDTO) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> clientAccounts = client.getAccounts();
        Account destinationAccount = accountRepository.findByNumber(createTransactionDTO.destinationAccount());
        double destinationAccountBalance = destinationAccount.getBalance();
        Account sourceAccount = accountRepository.findByNumber(createTransactionDTO.sourceAccount());
        double sourceAccountBalance = sourceAccount.getBalance();
        double transactionAmount = createTransactionDTO.amount();
        String description = createTransactionDTO.description();
        LocalDateTime date = LocalDateTime.now();
        TransactionType transactionType;

        try {
            transactionType = TransactionType.valueOf(createTransactionDTO.transactionType().toString().toUpperCase());
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Tipo de transaccion invalido", HttpStatus.BAD_REQUEST);
        }

        if (destinationAccount == null) {
            return new ResponseEntity<>("Destination account not found", HttpStatus.NOT_FOUND);
        }

        if (sourceAccount == null) {
            return new ResponseEntity<>("Source account not found", HttpStatus.NOT_FOUND);
        }

        if (!sourceAccount.getClient().equals(client)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


        if (transactionAmount <= 0) {
            return new ResponseEntity<>("Amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        if (sourceAccount.getBalance() < transactionAmount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.BAD_REQUEST);
        }

        if (sourceAccount.getNumber().equals(destinationAccount.getNumber())) {
            return new ResponseEntity<>("Source and destination accounts cannot be the same", HttpStatus.BAD_REQUEST);
        }


        if (description == null || description.trim().isEmpty()) {
            return new ResponseEntity<>("Description cannot be empty", HttpStatus.BAD_REQUEST);
        }

        sourceAccountBalance = sourceAccountBalance - transactionAmount;
        sourceAccount.setBalance(sourceAccountBalance);
        destinationAccountBalance = destinationAccountBalance + transactionAmount;
        destinationAccount.setBalance(destinationAccountBalance);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        Transaction newTransactionDebit = new Transaction(TransactionType.DEBIT, transactionAmount, description, date, sourceAccount);
        Transaction newTransactionCredit = new Transaction(TransactionType.CREDIT, transactionAmount, description, date, destinationAccount);

        transactionRepository.save(newTransactionDebit);
        transactionRepository.save(newTransactionCredit);

        return new ResponseEntity<>("Transaction created", HttpStatus.CREATED);
    }
}
