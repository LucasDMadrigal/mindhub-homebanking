package com.mindhub.homebanking.Utils;

import java.util.Random;

public class GenerateCardNumber {
    public static String cardNumber(){
        Random random = new Random();

        String firstGroup = String.valueOf(random.nextInt(1111) + 9999);
        String secoundGroup = String.valueOf(random.nextInt(1111) + 9999);
        String thirthGroup = String.valueOf(random.nextInt(1111) + 9999);
        String fourthGroup = String.valueOf(random.nextInt(1111) + 9999);
        String result = String.join("-", firstGroup, secoundGroup,thirthGroup,fourthGroup);
        return result;
    }
}
