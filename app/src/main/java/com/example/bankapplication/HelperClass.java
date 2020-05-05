package com.example.bankapplication;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

public class HelperClass { //Luokka avustaa Database luokan getAccountBalance -metodia.
    // Tarkoitus on tallentaa hetkellisesti Arraylist bListiin halutun käyttäjän balanssi, jotta osataan kirjoittaa tietokantaan uusi balanssi käyttäjälle.

    public float balance;
    public String email2 = "";
    protected static ArrayList<String> bList = new ArrayList<>();
    private static HelperClass b = null;

   protected void saveBalance(String balance_string) {
       bList.add(balance_string);
   } // Tämä


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
    protected void randomNumber(){


    }
    protected ArrayList<String> populateSpinner(String mEmail) {

       Bank bank = Bank.getInstance();

        ArrayList<String> arr = bank.arraylistOfAccounts(mEmail);
        ArrayList nameList;

        for (String a : arr) {
            System.out.println(a);
        }

        nameList = new ArrayList(); //Täytetään ensiksi lista
        nameList.add("regularAccount");
        nameList.add("creditAccount");
        nameList.add("savingsAccount");

        for (String s : arr) {   //Jos käyttäjällä on jo R-, C- tai S-tili niin poistetaan se pudotusvalikosta. Käyttäjällä saa olla max 1 tili/tilityyppi
            char first_letter = s.charAt(0);
            String account_mark = "" + first_letter;  //Let's get the account mark (which is either R, C, or S) to know which account user is using.
            if (account_mark.equals("R"))
                nameList.remove("regularAccount");
            else if (account_mark.equals("C"))
                nameList.remove("creditAccount");
            else if (account_mark.equals("S"))
                nameList.remove("savingsAccount");
        }

        return nameList;

    }

    protected String getCardNumber(){
        String cardNumber = "";
        final Random random = new Random();

        for(int i =0; i<4; i++){
            for(int j=0; j<4;j++){
                int a = random.nextInt(10);
                cardNumber = cardNumber + a;
            }
            cardNumber = cardNumber + " ";
        }
        return cardNumber;
    }



}
