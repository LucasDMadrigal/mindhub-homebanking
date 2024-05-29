//package com.mindhub.homebanking.DTO;
//
//public class TransactionDTO {
//}

package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;

public class TransactionDTO {
    private LocalDateTime date;
    private String description;
    private double amount;
    private TransactionType transactionType;
    public TransactionDTO(Transaction transaction) {
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
        this.transactionType = transaction.getTransactionType();
    }

    public LocalDateTime getDate() {
        return date;
    }


    public String getDescription() {
        return description;
    }


    public double getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
