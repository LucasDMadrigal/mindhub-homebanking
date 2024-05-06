package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }


    @Bean
    public CommandLineRunner initialData(ClientRepository ClientRepository, AccountRepository AccountRepository) {
        return (args) -> {
            System.out.println("Holis");
            Client cliente1 = new Client("Melba", "Morel","melba@mindhub.com");
            Client cliente2 = new Client("Rony", "Colleman","rony@mindhub.com");
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);

            ClientRepository.save(cliente1);
            ClientRepository.save(cliente2);

            Account cuenta1 = new Account("653219132",today, 5000,  cliente1);
            Account cuenta2 = new Account("9165516951",tomorrow, 7500,  cliente1);

            AccountRepository.save(cuenta1);
            AccountRepository.save(cuenta2);
        };
    }
}
/**
 * Name: Melba
 * Last Name: Morel
 * Email: melba@mindhub.com
 */