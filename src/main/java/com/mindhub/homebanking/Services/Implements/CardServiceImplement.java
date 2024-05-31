package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    CardRepository cardRepository;
    @Override
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardDTO> getCardsDto() {
        return getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
