package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TransactionType transactionType;
    private Double amount;
    private String description;
    private LocalDateTime date = LocalDateTime.now();


    /*
     * RELACION MANY TO ONE CON ACCOUNTS
     * */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, Double amount, String description, LocalDateTime date, Account account) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
//    public void addTransaction (Transaction transaction){
//        account.setClient(this);
//        accounts.add(account);
//    }
}
