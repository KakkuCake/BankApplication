package com.example.bankapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Bank {

    private static Bank b = null;
    private static ArrayList<Account> mAccounts = new ArrayList<>();
    private static ArrayList<BankCard> mCards = new ArrayList<>();
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

    public void addBankCard(String email, String card_number, float balance, float withdraw_limit) {
        mCards.add(new BankCard(email, card_number, balance, withdraw_limit));
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

    public void clearArrayLists() { //Tehdään tämä uloskirjautumisen yhteydessä, jotta luokkamuuttuja unohtaa arraylistin sisältämän datan.
        mAccounts.clear();
        mCards.clear();

    }

    public void depositCard(String email, float amount) {
        for (BankCard card : mCards)  {
            if (card.getEmail().equals(email)) {
                card.setBalance(amount);
            }
        }
    }

    public void withdrawCard(String email, float amount) {
        for (BankCard card : mCards)  {
            if (card.getEmail().equals(email)) {
                card.setBalance(-amount);
            }
        }
    }

    public void setNewWithdrawLimit(String email, float new_limit) {
        for (BankCard card : mCards) {
            if (card.getEmail().equals(email)) {
                card.setWithdrawLimit(new_limit);
            }
        }
    }

    public BankCard returnCard(String email) {
        for (BankCard card: mCards) {
            if (email.equals(card.getEmail()));
                return card;
        }
        return null;
    }

    public void writeTransaction(String accNum, String transactionType, String amount, Context context) {
        try {
            OutputStreamWriter file = new OutputStreamWriter(context.openFileOutput("transactions.csv", Context.MODE_APPEND));
            SimpleDateFormat formatter= new SimpleDateFormat("HH:mm dd.MM.yy");
            Date date = new Date(System.currentTimeMillis());
            file.write( accNum + ";" + transactionType + ";" + amount + "€;" + formatter.format(date) + " \n");
            file.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public String readTransaction(Context context, String account_number){
        String transactions = "";
        String line = "";
        try {
            InputStream file = context.openFileInput("transactions.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            while ((line = br.readLine()) != null) {
                String[] lista = line.split(";"); //accNum;type;amount
                if(lista[0].equals(account_number)) {
                    transactions = transactions + lista[1] + " " + lista[2] + " " + lista[3] + "\n\n";
                }else{
                }
            }
            System.out.println(transactions);
            br.close();
            file.close();

        }catch (FileNotFoundException e){
            System.out.println("error");
        }catch (IOException e){
            System.out.println("error");
        }
        return transactions;
    }

    public static Bank getInstance() {
        if (b == null) {
            return new Bank();
        }
        return b;
    }


}
