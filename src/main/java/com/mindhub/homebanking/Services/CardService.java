package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.DTO.CreateCardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    List<Card> getCards();
    List<CardDTO> getCardsDto();
    void saveCard(Card card);
}
