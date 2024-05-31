package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.DTO.CreateCardDTO;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Utils.GenerateCVVNumber;
import com.mindhub.homebanking.Utils.GenerateCardNumber;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import jdk.jfr.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;
    @GetMapping("/")
    public ResponseEntity<?> getCards() {
        List<Card> cardList = cardService.getCards();
        List<CardDTO> cardDTOList = cardService.getCardsDto();

        if (cardList.isEmpty()) {
            return new ResponseEntity<>("No se encontraron tarjetas", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cardDTOList, HttpStatus.OK);
        }
    }

    @PostMapping("/current/create")
    public ResponseEntity<?> createCards(Authentication authentication, @RequestBody CreateCardDTO createCardDTO) {
        Client client = clientService.getClientByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }

        if (client.getCards().size() >= 3) {
            return new ResponseEntity<>("El cliente ya tiene 3 tarjetas", HttpStatus.FORBIDDEN);
        }

        CardColor cardColor;
        CardType cardType;
        try {
            cardColor =  CardColor.valueOf(createCardDTO.cardColor().toString().toUpperCase());
            cardType = CardType.valueOf(createCardDTO.cardType().toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("color de tarjeta invalido", HttpStatus.BAD_REQUEST);
        }

        String cardNumber = GenerateCardNumber.cardNumber();
        String cvvNumber = GenerateCVVNumber.CVVNumber();
        LocalDate creationDate = LocalDate.now();
        LocalDate expirationDate = creationDate.plusYears(5);
        Card newCard = new Card(cardNumber, cvvNumber, cardType, cardColor, creationDate, expirationDate, client);
        cardService.saveCard(newCard);

        return new ResponseEntity<>("Tarjeta creada", HttpStatus.CREATED);
    }
}
