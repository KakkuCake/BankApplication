package com.example.bankapplication;

import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> aList = new ArrayList();

    public void addRegularAccount(String acNumber, int amount) {
        RegularAccount rAccount = new RegularAccount(acNumber, amount);
        aList.add(rAccount);
    }

    public void addCreditAccount(String acNumber, int amount, int credit) {
        CreditAccount crAccount = new CreditAccount(acNumber, amount, credit);
        aList.add(crAccount);
    }

    public void depositMoney(String acNumber, int amount) {
        //System.out.println("Depositing to the account: " + acNumber + " the amount " + amount);
        for (Account account : aList)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(amount);
            }
        }
    }

    public void withdrawMoney(String acNumber, int amount) {
        //System.out.println("Withdrawing to the account: " + acNumber + " the amount " + amount);
        for (Account account : aList)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(-amount);
            }
        }
    }

    public void removeAccount(String acNumber) {
        //System.out.println("Account removed");
        int i = 0;
        for (Account account : aList)  {
            if (account.getAcNumber().equals(acNumber)) {
                aList.remove(i);
                System.out.println("Account removed.");
                break;
            }
            i++;
        }
    }

    public void printAccountInformation(String acNumber, int amount) {
        //System.out.println("Searching for account: " + acNumber);
        for (Account account: aList) {
            if (account.getAcNumber().equals(acNumber)) {
                System.out.println("Account number: " + acNumber + " Amount of money: " + account.getBalance());
            }
        }
    }

    public void printAllAccounts() {
        System.out.println("All accounts:");
        for (Account account: aList) {
            System.out.print("Account number: " + account.getAcNumber() +
                    " Amount of money: " + account.getBalance());
            if (account instanceof CreditAccount) {
                System.out.println(" Credit limit: " + ((CreditAccount) account).getCreditLimit());
            }
            System.out.println();
        }
    }


}
