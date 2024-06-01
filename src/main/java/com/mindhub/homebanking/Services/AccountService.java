package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAccounts();
    List<AccountDTO> getAccountsDto();
    Account getAccountById(long id);
    Optional<Account> getAccountByNumber(String number);
    AccountDTO getAccountDto(Account account);
    void saveAccount(Account account);
}
