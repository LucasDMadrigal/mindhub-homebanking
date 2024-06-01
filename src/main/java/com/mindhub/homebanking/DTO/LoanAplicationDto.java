package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

    public record LoanAplicationDto(long id, double amount, int payments, String accountNumber, String description) {
}
