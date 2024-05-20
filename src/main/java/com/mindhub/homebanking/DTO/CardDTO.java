package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;

import java.time.LocalDate;

public class CardDTO {
    private long id;

    private String cardholder;
    private String number;
    private String CVV;

    private CardType type;
    private CardColor color;


    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardholder = card.getCardholder();
        this.number = card.getNumber();
        this.CVV = card.getCVV();
        this.type = card.getType();
        this.color = card.getColor();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
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
}
