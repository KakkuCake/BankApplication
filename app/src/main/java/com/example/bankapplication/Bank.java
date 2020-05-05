package com.example.bankapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Bank {

    private static Bank b = null;
    private static ArrayList<Account> mAccounts = new ArrayList<>();
    Context context;

    public void addRegularAccount(String email, String account_number, float balance) {
        mAccounts.add(new RegularAccount(email, account_number, balance));
    }

    public void addCreditAccount(String email, String account_number, float balance, float credit) {
        mAccounts.add(new CreditAccount(email, account_number, balance, credit));
    }

    public void addSavingsAccount(String email, String account_number, float balance) {
        mAccounts.add(new SavingsAccount(email, account_number, balance));
    }

    public void depositMoney(String acNumber, float amount) {
        for (Account account : mAccounts)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(amount);
            }
        }
    }

    public void withdrawMoney(String acNumber, float amount) {
        for (Account account : mAccounts)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(-amount);
            }
        }
    }

    public void setNewCreditLimit(String account_number, float new_credit) {
        for (Account account : mAccounts) {
            if (account instanceof CreditAccount) {
                if (account_number.equals(account.getAcNumber()))
                    ((CreditAccount) account).setCreditLimit(new_credit);
            }
        }
    }

    public String getInterestRate(String account_number) {
        for (Account account : mAccounts) {
            if (account instanceof SavingsAccount) {
                if (account_number.equals(account.getAcNumber()))
                    return String.valueOf(((SavingsAccount) account).getInterestRate());
            }
        }
        return null;
    }

    public ArrayList<String> arraylistOfAccounts(String email) {
        ArrayList<String> accounts = new ArrayList<String>();
        String accountInformation;
        for (Account account: mAccounts) {
            if (email.equals(account.getEmail())) {
                accountInformation = account.getAcNumber();
                accounts.add(accountInformation);
            }
        }
        return accounts;
    }

    public Account returnAccount(String account_number) {
        for (Account account: mAccounts) {
            if (account_number.equals(account.getAcNumber()))
               return account;
        }
        return null;
    }

    public void clearArrayList() { //Tehdään tämä uloskirjautumisen yhteydessä, jotta luokkamuuttuja unohtaa arraylistin sisältämän datan.
        mAccounts.clear();
        clear(); // users.csv
    }


    public static Bank getInstance() {
        if (b == null) {
            return new Bank();
        }
        return b;
    }

    public void writeTransaction(String transactionType, String amount) {
        try {

            OutputStreamWriter file = new OutputStreamWriter(context.openFileOutput("users.csv", Context.MODE_APPEND));
            file.write( transactionType + ";" + amount + "\n");
            file.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void readTransaction(){
        try {
            InputStream file = context.openFileInput("users.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] lista = line.split(";");
                System.out.println(lista[1] + "####################");
            }
            br.close();
            file.close();
        }catch (FileNotFoundException e){
            System.out.println("error");
        }catch (IOException e){
            System.out.println("error");
        }
    }
    public void clear() {
        try {
            OutputStreamWriter file = new OutputStreamWriter(context.openFileOutput("users2.csv", MODE_PRIVATE));
            file.write("");
            file.flush();
            file.close();
        }catch (IOException e){
            System.out.println("joo");
        }
    }

}
