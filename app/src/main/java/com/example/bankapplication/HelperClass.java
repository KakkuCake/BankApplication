package com.example.bankapplication;

import java.util.ArrayList;

public class HelperClass { //Luokka avustaa Database luokan getAccountBalance -metodia.

    public float balance;
    protected static ArrayList<String> bList = new ArrayList<>();
    private static HelperClass b = null;

   protected void saveBalance(String balance_string) {
       bList.add(balance_string);
   }


    protected String getBalance() {
        for (String s : bList) {
            return s;
        }
        return null;
    }

    public static HelperClass getInstance() {
        if (b == null) {
            return new HelperClass();
        }
        return b;
    }

    protected void clearList() {
       bList.clear();
    }



}
