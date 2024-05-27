package com.mindhub.homebanking.Utils;

import java.util.Random;

public class GenerateAccountNumber {
    public static String accountNumber() {
        Random random = new Random();
        int number = random.nextInt(90000000) + 10000000;
        return "VIN-" + number;
    }
}
