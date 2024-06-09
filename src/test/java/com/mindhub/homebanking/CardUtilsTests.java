package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.GenerateCVVNumber;
import com.mindhub.homebanking.Utils.GenerateCardNumber;
import org.aspectj.weaver.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated() {
        String cardNumber = GenerateCardNumber.cardNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }

    @Test
    public void CVVNumberIsCreated() {
        String cardNumber = GenerateCVVNumber.CVVNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }
}
