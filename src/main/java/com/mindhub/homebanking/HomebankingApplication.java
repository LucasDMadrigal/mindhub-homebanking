package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initialData(ClientRepository ClientRepository, AccountRepository AccountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {
            System.out.println("Holis");
            Client cliente1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("abc123"));
            Client cliente2 = new Client("Rony", "Colleman", "rony@mindhub.com", passwordEncoder.encode("abc123"));
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

            Set<Integer> mortgagePayments = Set.of(6, 12, 24, 36, 48, 60);

            Set<Integer> personalLoanPayments = Set.of(6, 12, 24);

            Set<Integer> automotiveLoanPayments = Set.of(6, 12, 24, 36);

            Loan mortgageLoan = new Loan("Mortgage", 500000.0, mortgagePayments);
            Loan personalLoan = new Loan("Personal", 100000.0, personalLoanPayments);
            Loan automotiveLoan = new Loan("Automotive", 300000.0, automotiveLoanPayments);

            loanRepository.save(mortgageLoan);
            loanRepository.save(personalLoan);
            loanRepository.save(automotiveLoan);


            ClientLoan cliente1mortage = new ClientLoan(400000, 60, cliente1, mortgageLoan);
            ClientLoan cliente1Personal = new ClientLoan(50000, 12, cliente1, personalLoan);

            clientLoanRepository.save(cliente1mortage);
            clientLoanRepository.save(cliente1Personal);

            cliente1.addClientLoan(cliente1mortage);
            cliente1.addClientLoan(cliente1Personal);

            ClientLoan cliente2Personal = new ClientLoan(100000, 24, cliente2, personalLoan);
            ClientLoan cliente2Automotive = new ClientLoan(200000, 36, cliente2, automotiveLoan);

            cliente2.addClientLoan(cliente2Personal);
            cliente2.addClientLoan(cliente2Automotive);

            clientLoanRepository.save(cliente2Personal);
            clientLoanRepository.save(cliente2Automotive);

            LocalDate startDate = LocalDate.now();
            LocalDate expirationDate = startDate.plusYears(5);
            String cardholderName1 = cliente1.getFirstName() + " " + cliente1.getLastName();
            String cardholderName2 = cliente2.getFirstName() + " " + cliente2.getLastName();
            String number = "1234-5678-9012-3456";
            String cvv = generateCVV();
            Card card1 = new Card(cardholderName1, number, cvv, CardType.DEBIT, CardColor.GOLD, startDate, expirationDate, cliente1);
            Card card2 = new Card(cardholderName1, number, cvv, CardType.CREDIT, CardColor.TITANIUM, startDate, expirationDate, cliente1);
            Card card3 = new Card(cardholderName2, number, cvv, CardType.CREDIT, CardColor.SILVER, startDate, expirationDate, cliente2);

            cardRepository.save(card1);
            cliente1.addCard(card1);

            cardRepository.save(card2);
            cliente1.addCard(card2);

            cardRepository.save(card3);
            cliente2.addCard(card3);
        };


    }

    private String generateCardNumber() {
        return "1234567890123456";
    }

    private String generateCVV() {
        return "123";
    }
}
/**
 * Name: Melba
 * Last Name: Morel
 * Email: melba@mindhub.com
 */

