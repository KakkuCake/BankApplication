package com.example.bankapplication;

public class CreditAccount extends Account {

    private int credit;
    public CreditAccount(String eM, String aN, int b, int c) {
        super(eM, aN, b);
        credit = c;
    }

    public int getCreditLimit() {
        return credit;
    }

    public void setBalance(int amount) {
        if (-credit <= balance + amount)
            this.balance += amount;

    }

}