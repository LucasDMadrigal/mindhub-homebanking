package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanAplicationDto;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientLoanService clientLoanService;
    @Autowired
    TransactionService transactionService;

    @GetMapping("/")
    public ResponseEntity<?> getLoans() {
        List<Loan> loanList = loanService.getLoans();
        List<LoanDTO> loanDTOList = loanService.getLoansDto();

        if (loanList.isEmpty()) {
            return new ResponseEntity<>("no se encuentran Loans", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(loanDTOList, HttpStatus.OK);
        }
    }

    @Transactional
    @PostMapping("/current")
    public ResponseEntity<?> applyLoans(Authentication authentication, @RequestBody LoanAplicationDto loanAplicationDto) {
        Client client = clientService.getClientByEmail(authentication.getName());
        Optional<Account> destinationAccountOptional = accountService.getAccountByNumber(loanAplicationDto.accountNumber());
        Loan loan = loanService.getLoanById(loanAplicationDto.id());
        int payments = loanAplicationDto.payments();
        double amount = loanAplicationDto.amount();
        double amountWithRate = 0.0;
        String description = loanAplicationDto.description() + " - loan approved";
        if (!destinationAccountOptional.isPresent()) {
            return new ResponseEntity<>("Source account not found", HttpStatus.NOT_FOUND);
        }
        Account destinationAccount = destinationAccountOptional.get();

        if (!destinationAccount.getClient().equals(client)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        //*//

        // Verificar que los datos no estén vacíos y que el monto y las cuotas no sean 0
        if (amount <= 0 || payments <= 0) {
            return new ResponseEntity<>("El monto y las cuotas deben ser mayores que cero", HttpStatus.BAD_REQUEST);
        }

        // Verificar que el préstamo exista
        if (loan == null) {
            return new ResponseEntity<>("Préstamo no encontrado", HttpStatus.NOT_FOUND);
        }


        // Verificar que la cuenta de destino pertenezca al cliente autenticado
        if (!destinationAccount.getClient().equals(client)) {
            return new ResponseEntity<>("No autorizado", HttpStatus.UNAUTHORIZED);
        }

        // Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if (amount > loan.getMaxAmount()) {
            return new ResponseEntity<>("El monto solicitado excede el monto máximo del préstamo", HttpStatus.BAD_REQUEST);
        }

        // Verificar que el número de cuotas esté entre las disponibles del préstamo
        if (!loan.getPayments().contains(payments)) {
            return new ResponseEntity<>("El número de cuotas no está disponible para este préstamo", HttpStatus.BAD_REQUEST);
        }


        String message = "non message";

        if (payments > 12) {
            amountWithRate = amount * 1.25;
            destinationAccount.setBalance(destinationAccount.getBalance()+amount);
        }
        if (payments == 12) {
            amountWithRate = amount * 1.2;
            destinationAccount.setBalance(destinationAccount.getBalance()+amount);

        }
        if (payments < 12) {
            amountWithRate = amount * 1.15;
            destinationAccount.setBalance(destinationAccount.getBalance()+amount);

        }
        Transaction transaction = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now(), destinationAccount);
        ClientLoan clientLoan = new ClientLoan(amountWithRate, payments, client, loan);

        client.addClientLoan(clientLoan);

        transactionService.saveTransaction(transaction);
        clientLoanService.saveClientLoan(clientLoan);
        accountService.saveAccount(destinationAccount);
        clientService.saveClient(client);

        return new ResponseEntity<>("loan created", HttpStatus.CREATED);
    }
}
