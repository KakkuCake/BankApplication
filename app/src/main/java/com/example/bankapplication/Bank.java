package com.example.bankapplication;

import java.util.ArrayList;

public class Bank {

    private static Bank b = null;
    private static ArrayList<Account> mAccounts = new ArrayList<>();

    public void addRegularAccount(String email, String account_number, float balance) {
        mAccounts.add(new RegularAccount(email, account_number, balance));
    }

    public void addCreditAccount(String email, String account_number, float balance, float credit) {
        mAccounts.add(new CreditAccount(email, account_number, balance, credit));
    }

    public void depositMoney(String acNumber, float amount) {
        //System.out.println("Depositing to the account: " + acNumber + " the amount " + amount);
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


    public void printAllAccounts(String email) {
        System.out.println("All accounts:");
        for (Account account: mAccounts) {
            if (email.equals(account.getEmail())) {
                System.out.print("Account number: " + account.getAcNumber() + " Amount of money: " + account.getBalance());
                if (account instanceof CreditAccount) {
                    System.out.println(" Credit limit: " + ((CreditAccount) account).getCreditLimit());
                }
            }
            System.out.println();
        }
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
    }


    public static Bank getInstance() {
        if (b == null) {
            return new Bank();
        }
        return b;
    }



}
