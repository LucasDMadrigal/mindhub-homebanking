package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;

    private LocalDate creationDate = LocalDate.now();

    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Account() {
    }

    public Account(String number, LocalDate creationDate, double balance, Client client) {
        this.id = id;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public long getId(){
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }
}
