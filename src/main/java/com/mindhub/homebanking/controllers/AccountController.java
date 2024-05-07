//package com.mindhub.homebanking.controllers;
//
//public class AccountController {
//}

package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

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
}
