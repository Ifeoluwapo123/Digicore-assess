package com.example.digicore.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HelperClass {

    private static int randomNumberLength = 10;
    private static String numbers = "0123456789";
    private static List<String> accountNumbers = new ArrayList();

    public static String generateNewAccountNumber(){
        Random random = new Random();
        char[] text = new char[randomNumberLength];

        for (int index = 0; index < randomNumberLength; index++) {
            text[index] = numbers.charAt(random.nextInt(numbers.length()));
        }

        if(accountNumbers.contains(new String(text))) generateNewAccountNumber();
        else accountNumbers.add(new String(text));

        return new String(text);
    }

    public static Date generateCurrentDateTime(){
        return new Date();
    }

}
