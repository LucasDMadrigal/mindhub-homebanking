package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.hibernate.annotations.Array;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
//    @Test
//    public void canCreateClient(){
//        Client cliente1 = new Client("Tita", "Morel", "melba@mindhub.com", "abc123", false);
//
//        Client savedClient = clientRepository.save(cliente1);
//
//        assertThat(savedClient, hasProperty("id", greaterThan(0L)));
//    }
//        @Test
//        public void testCreateAccountForClient() {
//            Client client = clientRepository.save(new Client("Jane", "Doe", "jane.doe@mindhub.com", "password456", false));
//            Account account = new Account("VIN-12345", LocalDate.now(), 1000, client);
//
//            client.addAccounts(account);
//            clientRepository.save(client);
//            assertThat(client.getAccounts(), hasItem(account));
//        }

//    @Test
//    public void testCreateCardForClient() {
//        Client client = clientRepository.save(new Client("Alice", "Smith", "alice.smith@mindhub.com", "password789", false));
//        Account account = accountRepository.save(new Account("VIN-54321", LocalDate.now(), 2000, client));
//        Card card = new Card("1234-5678-9876-5432","123" , CardType.CREDIT, CardColor.GOLD,LocalDate.now(), LocalDate.now().plusYears(3), client);
//
//        client.addCard(card);
//        clientRepository.save(client);
//        assertThat(client.getCards(), hasItem(card));
//    }
    }
