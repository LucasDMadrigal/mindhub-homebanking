package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
    public CommandLineRunner initialData(ClientRepository ClientRepository, AccountRepository AccountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
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

//            ClientLoan clientLoan1 = new ClientLoan()

            ClientLoan cliente1mortage = new ClientLoan(400000, 60, cliente1, mortgageLoan);
            ClientLoan cliente1Personal = new ClientLoan(50000, 12, cliente1, personalLoan);

            clientLoanRepository.save(cliente1mortage);
            clientLoanRepository.save(cliente1Personal);

            cliente1.addClientLoan(cliente1mortage);
            cliente1.addClientLoan(cliente1Personal);

            // Crear ClientLoan entities para el otro cliente
            ClientLoan cliente2Personal = new ClientLoan(100000, 24, cliente2, personalLoan);
            ClientLoan cliente2Automotive = new ClientLoan(200000, 36, cliente2, automotiveLoan);

            cliente2.addClientLoan(cliente2Personal);
            cliente2.addClientLoan(cliente2Automotive);

            clientLoanRepository.save(cliente2Personal);
            clientLoanRepository.save(cliente2Automotive);

        };
    }
}
/**
 * Name: Melba
 * Last Name: Morel
 * Email: melba@mindhub.com
 */

