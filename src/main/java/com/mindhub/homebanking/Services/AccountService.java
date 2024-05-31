package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();
    List<AccountDTO> getAccountsDto();
    Account getAccountById(long id);
    AccountDTO getAccountDto(Account account);
    void saveAccount(Account account);
}
