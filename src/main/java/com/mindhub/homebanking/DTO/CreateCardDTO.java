package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;

public record CreateCardDTO(CardType cardType, CardColor cardColor) {
}
