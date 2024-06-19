package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.enums.TransactionType;

public record CreateTransactionDTO(double amount, String sourceAccount, String destinationAccount, String description) {
}
