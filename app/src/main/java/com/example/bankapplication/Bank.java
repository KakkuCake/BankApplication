package com.example.bankapplication;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private static Bank b = null;

    private static ArrayList<Account> mAccounts = new ArrayList<>();

    public void addRegularAccount(String email, String account_number, int balance) {
        mAccounts.add(new RegularAccount(email, account_number, balance));
    }

    public void addCreditAccount(String email, String account_number, int balance, int credit) {
        mAccounts.add(new CreditAccount(email, account_number, balance, credit));
    }

    public void depositMoney(String acNumber, int amount) {
        //System.out.println("Depositing to the account: " + acNumber + " the amount " + amount);
        for (Account account : mAccounts)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(amount);
            }
        }
    }

    public void withdrawMoney(String acNumber, int amount) {
        //System.out.println("Withdrawing to the account: " + acNumber + " the amount " + amount);
        for (Account account : mAccounts)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(-amount);
            }
        }
    }


    public void printAllAccounts() {
        System.out.println("All accounts:");
        for (Account account: mAccounts) {
            System.out.print("Account number: " + account.getAcNumber() +
                    " Amount of money: " + account.getBalance());
            if (account instanceof CreditAccount) {
                System.out.println(" Credit limit: " + ((CreditAccount) account).getCreditLimit());
            }
            System.out.println();
        }
    }


    public static Bank getInstance() {
        if (b == null) {
            return new Bank();
        }
        return b;
    }



}
