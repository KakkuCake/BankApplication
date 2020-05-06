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
        mAccounts.add(new RegularAccount(email, account_number, balance)); //creates a new instance of an account (Type R,C,S) and ads it to mAccounts ArrayList.
    }

    public void addCreditAccount(String email, String account_number, float balance, float credit) {
        mAccounts.add(new CreditAccount(email, account_number, balance, credit));//creates a new instance of an account (Type R,C,S) and ads it to mAccounts ArrayList.
    }

    public void addSavingsAccount(String email, String account_number, float balance) {
        mAccounts.add(new SavingsAccount(email, account_number, balance));//creates a new instance of Account (Type R,C,S) and ads it to mAccounts ArrayList.
    }

    public void addBankCard(String email, String card_number, float balance, float withdraw_limit) {
        mCards.add(new BankCard(email, card_number, balance, withdraw_limit));//creates a new instance of BankCard and ads it to mCards Arraylist.
    }

    public void depositMoney(String acNumber, float amount) { //Searches the mAccounts arraylist for a specific account number to which money(amount) is deposited.
        for (Account account : mAccounts)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(amount);
            }
        }
    }

    public void withdrawMoney(String acNumber, float amount) { //Searches the mAccounts arraylist for a specific account number to which money(amount) is withdrawn from.
        for (Account account : mAccounts)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(-amount);
            }
        }
    }


    public void setNewCreditLimit(String account_number, float new_credit) { //Searches the mAccounts arraylist for a specific account which credit limit is to be changed
        for (Account account : mAccounts) {
            if (account instanceof CreditAccount) {
                if (account_number.equals(account.getAcNumber()))
                    ((CreditAccount) account).setCreditLimit(new_credit);
            }
        }
    }

    public String getInterestRate(String account_number) { //Searches the mAccounts arraylist for a specific savings account and returns the interest rate
        for (Account account : mAccounts) {
            if (account instanceof SavingsAccount) {
                if (account_number.equals(account.getAcNumber()))
                    return String.valueOf(((SavingsAccount) account).getInterestRate());
            }
        }
        return null;
    }

    public ArrayList<String> arraylistOfAccounts(String email) { // Returns all accounts of the current, signed in user
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

    public Account returnAccount(String account_number) { // Returns specific account of the current, signed in user
        for (Account account: mAccounts) {
            if (account_number.equals(account.getAcNumber()))
               return account;
        }
        return null;
    }

    public void clearArrayLists() { //This is executed during log out, to clear the ArrayLists of data.
        mAccounts.clear();
        mCards.clear();
    }

    public void depositCard(String email, float amount) { //Gets user's card and adds money to it.
        for (BankCard card : mCards)  {
            if (card.getEmail().equals(email)) {
                card.setBalance(amount);
            }
        }
    }

    public void withdrawCard(String email, float amount) { //Gets user's card and withdraws money from it.
        for (BankCard card : mCards)  {
            if (card.getEmail().equals(email)) {
                card.setBalance(-amount);
            }
        }
    }

    public void setNewWithdrawLimit(String email, float new_limit) { //Gets user's card and sets a new withdrawal limit
        for (BankCard card : mCards) {
            if (card.getEmail().equals(email)) {
                card.setWithdrawLimit(new_limit);
            }
        }
    }

    public BankCard returnCard(String email) { //Returns the instance of user's card
        for (BankCard card: mCards) {
            if (email.equals(card.getEmail()));
                return card;
        }
        return null;
    }
    //Writes transactions to transactions.csv -file. Text is formatted as follows: "AccountNumber;TransactionType;Amount;DATE" =>
    // 'C1234567;Deposit;+40€;21:20 6.5.20'
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

    //Reads specific transactions according to users account_number from transactions.csv and writes them to /String transactions = "";/
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
    //helps to create a new instance of Bank
    public static Bank getInstance() {  //We used Singleton principle in this class.
        if (b == null) {
            return new Bank();
        }
        return b;
    }


}
