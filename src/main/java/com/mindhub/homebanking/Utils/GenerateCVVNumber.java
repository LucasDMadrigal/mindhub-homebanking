package com.mindhub.homebanking.Utils;

import java.util.Random;

public class GenerateCVVNumber {
    public static String CVVNumber(){
        Random random = new Random();
        String CVV = String.valueOf(random.nextInt(001) + 999);
        return String.valueOf(CVV);
    }
}
