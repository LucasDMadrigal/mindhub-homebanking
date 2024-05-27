//package com.mindhub.homebanking.controllers;
//
//public class AccountController {
//}

package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Utils.GenerateAccountNumber;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAccounts() {


        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountDTOList = accountList.stream().map(AccountDTO::new).collect(Collectors.toList());

        if (accountList.isEmpty()) {
            return new ResponseEntity<>("No se encontraron cuentas", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accountDTOList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable long id) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account != null) {
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping("/create")
//    public ResponseEntity<?> createAccount(Authentication authentication) {
//        boolean hasClientAuthority = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch(authority -> authority.equals("ROLE_CLIENT"));
//
//        if (!hasClientAuthority) {
//            return new ResponseEntity<>("Access denegadoo", HttpStatus.FORBIDDEN);
//        }
//
//        Client client = clientRepository.findByEmail(authentication.getName());
//
//        if (client == null) {
//            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
//        }
//
//        if (client.getAccounts().size() >= 3) {
//            return new ResponseEntity<>("Client already has 3 accounts", HttpStatus.FORBIDDEN);
//        }
//
//        String accountNumber = generateAccountNumber();
//        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0, client);
//
//        accountRepository.save(newAccount);
//
//        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
//    }

    @PostMapping("/current/create")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Client already has 3 accounts", HttpStatus.FORBIDDEN);
        }

        String accountNumber = GenerateAccountNumber.accountNumber();
        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0, client);

        accountRepository.save(newAccount);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }

//    private String generateAccountNumber() {
//        Random random = new Random();
//        int number = random.nextInt(90000000) + 10000000;
//        return "VIN-" + number;
//    }
}

