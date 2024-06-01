package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> getAccountsDto() {
        return getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Account> getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public AccountDTO getAccountDto(Account account) {
        return new AccountDTO(account);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
