package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }


    @Bean
    public CommandLineRunner initialData(ClientRepository ClientRepository, AccountRepository AccountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository) {
        return (args) -> {
            System.out.println("Holis");
            Client cliente1 = new Client("Melba", "Morel", "melba@mindhub.com");
            Client cliente2 = new Client("Rony", "Colleman", "rony@mindhub.com");
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);

            ClientRepository.save(cliente1);
            ClientRepository.save(cliente2);

            Account cuenta1 = new Account("653219132", today, 5000, cliente1);
            Account cuenta2 = new Account("9165516951", tomorrow, 7500, cliente1);

            AccountRepository.save(cuenta1);
            AccountRepository.save(cuenta2);

            Transaction transaction1 = new Transaction(TransactionType.CREDIT, 987654.66, "transact 1", LocalDateTime.now(), cuenta1);
            Transaction transaction2 = new Transaction(TransactionType.DEBIT, 6548.66, "transact 2", LocalDateTime.now(), cuenta1);

            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);

            Set<Integer> mortgagePayments = Set.of(6, 12, 24,36,48, 60);

            Set<Integer> personalLoanPayments = Set.of(6, 12, 24);

            Set<Integer> automotiveLoanPayments = Set.of(6, 12, 24,36);

            Loan mortgageLoan = new Loan("Mortgage", 500000.0, mortgagePayments);
            Loan personalLoan = new Loan("Personal", 100000.0, personalLoanPayments);
            Loan automotiveLoan = new Loan("Automotive", 300000.0, automotiveLoanPayments);

            loanRepository.save(mortgageLoan);
            loanRepository.save(personalLoan);
            loanRepository.save(automotiveLoan);
        };
    }
}
/**
 * Name: Melba
 * Last Name: Morel
 * Email: melba@mindhub.com
 */

