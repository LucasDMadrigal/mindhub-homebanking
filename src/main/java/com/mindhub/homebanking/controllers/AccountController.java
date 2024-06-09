//package com.mindhub.homebanking.controllers;
//
//public class AccountController {
//}

package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
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
    AccountService accountService;

    @Autowired
    ClientService clientService;
    @GetMapping("/")
    public ResponseEntity<?> getAccounts(Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());
        String role = authentication.getAuthorities().toString();

        if (client == null || role != "ADMIN"){
            return new ResponseEntity<>("No tiene accesos", HttpStatus.FORBIDDEN);
        }
        List<Account> accountList = accountService.getAccounts();
        List<AccountDTO> accountDTOList = accountService.getAccountsDto();

        if (accountList.isEmpty()) {
            return new ResponseEntity<>("No se encontraron cuentas", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accountDTOList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable long id, Authentication authentication) {
        Account account = accountService.getAccountById(id);
        Client client = clientService.getClientByEmail(authentication.getName());
        String role = authentication.getAuthorities().toString();
        if (account != null) {
            AccountDTO accountDTO = accountService.getAccountDto(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/current/create")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Client already has 3 accounts", HttpStatus.FORBIDDEN);
        }

        String accountNumber = GenerateAccountNumber.accountNumber();
        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0, client);

        accountService.saveAccount(newAccount);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }

}

