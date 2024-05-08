package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    private Set<Integer> payments = new HashSet<>();

//    @ManyToMany
//    private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    /*
    * CONSTRUCTORES
    * */
    public Loan() {
    }

    public Loan(String name, double maxAmount, Set<Integer> payments) {
        this.id = id;
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }


    /*
     * GETTERS Y SETTERS
     * */

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

//    public Set<Client> getClients() {
//        return clients;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }

//    public void setClients(Set<Client> clients) {
//        this.clients = clients;
//    }
}
