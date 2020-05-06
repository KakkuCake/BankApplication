package com.example.bankapplication;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

public class HelperClass {
  // this class is made only to help with certain things

    public float balance;
    public String email2 = "";
    protected static ArrayList<String> bList = new ArrayList<>();
    private static HelperClass b = null;

   protected void saveBalance(String balance_string) { // This ArrayList contains one balance, to help with things
       bList.add(balance_string);
   }


    protected String getBalance() { //This method returns one saved balance
        for (String s : bList) {
            return s;
        }
        return null;
    }

    public static HelperClass getInstance() { // we used singleton principle in this class
        if (b == null) {
            return new HelperClass();
        }
        return b;
    }

    protected void clearList() { // this method clears the one balance from the ArrayList
       bList.clear();
    }


    protected ArrayList<String> populateSpinner(String mEmail) { //This method populates the spinner from which you choose the account type you want to create

       Bank bank = Bank.getInstance();

        ArrayList<String> arr = bank.arraylistOfAccounts(mEmail);
        ArrayList nameList;

        for (String a : arr) {
            System.out.println(a);
        }

        nameList = new ArrayList();
        nameList.add("regularAccount");
        nameList.add("creditAccount");
        nameList.add("savingsAccount");

        for (String s : arr) {   //Account number's first letter determines the account type for ex. C1234567 is credit account.
            char first_letter = s.charAt(0);
            String account_mark = "" + first_letter;
            if (account_mark.equals("R"))
                nameList.remove("regularAccount");
            else if (account_mark.equals("C"))
                nameList.remove("creditAccount");
            else if (account_mark.equals("S"))
                nameList.remove("savingsAccount");
        }

        return nameList;

    }

    protected String getCardNumber(){ // When user creates a bankcard, this method generates the 16-digit number for it.
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