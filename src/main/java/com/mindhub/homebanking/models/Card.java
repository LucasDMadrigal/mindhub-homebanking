package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardholder;
    private String number;
    private String CVV;

    private CardType type;
    private CardColor color;


    private LocalDate fromDate;
    private LocalDate thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardholder, String number, String CVV, CardType type, CardColor color, LocalDate fromDate, LocalDate thruDate, Client client) {
        this.cardholder = cardholder;
        this.number = number;
        this.CVV = CVV;
        this.type = type;
        this.color = color;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public String getNumber() {
        return number;
    }

    public String getCVV() {
        return CVV;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
