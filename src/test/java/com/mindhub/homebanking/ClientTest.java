//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.Services.LoanService;
//import com.mindhub.homebanking.enums.CardColor;
//import com.mindhub.homebanking.enums.CardType;
//import com.mindhub.homebanking.models.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import static org.hamcrest.Matchers.*;
//@SpringBootTest
//public class ClientTest {
//
//    @Autowired
//    LoanService loanService;
//    @Test
//    public void testCreateClient() {
//        Client client = new Client("John", "Doe", "john.doe@mindhub.com", "password123", false);
//
//        assertThat(client.getFirstName(), is("John"));
//        assertThat(client.getLastName(), is("Doe"));
//        assertThat(client.getEmail(), is("john.doe@mindhub.com"));
//        assertThat(client.getPassword(), is("password123"));
//        assertThat(client.getIsAdmin(), is(false));
//    }
//
//    @Test
//    public void testAddAccountToClient(){
//        LocalDate today = LocalDate.now();
//        LocalDate tomorrow = today.plusDays(1);
//
//        Client cliente1 = new Client("Melba", "Morel", "melba@mindhub.com", "abc123", false);
//        Account cuenta1 = new Account("VIN-19132", today, 5000, cliente1);
//
//        cliente1.addAccounts(cuenta1);
//
//        assertThat(cliente1.getAccounts(), hasItem(cuenta1));
//    }
//
//    @Test
//    public void testCreateCardForClient() {
//        LocalDate today = LocalDate.now();
//        Client client = new Client("Alice", "Smith", "alice.smith@mindhub.com", "password789", false);
//        Account account = new Account("VIN-54321", today, 2000, client);
//        Card card = new Card("1234-5678-9876-5432","123" , CardType.CREDIT, CardColor.GOLD,LocalDate.now(), LocalDate.now().plusYears(3), client);
//
//        assertThat(card.getNumber(), is("1234-5678-9876-5432"));
//        assertThat(card.getCVV(), is("123"));
//        assertThat(card.getClient(), is(client));
//    }
//
//    @Test
//    public void testRequestLoanForClient() {
//        LocalDate today = LocalDate.now();
//        Client cliente1 = new Client("Melba", "Morel", "melba@mindhub.com", "abc123", false);
//        Account cuenta1 = new Account("VIN-19132", today, 3000, cliente1);
//        Loan loan =  loanService.getLoanById(1);
//        ClientLoan clientLoan = new ClientLoan(5000, 12, cliente1,loan);
//
//        assertThat(clientLoan.getAmount(), is(5000.0));
////        assertThat(cuenta1.getBalance(), is(8000.0));
//        assertThat(clientLoan.getClient(), is(cliente1));
//    }
//}
