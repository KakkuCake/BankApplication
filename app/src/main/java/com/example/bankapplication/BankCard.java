package com.example.bankapplication;

public class BankCard {

    protected String email, card_number;
    protected float balance, withdraw_limit;

    public BankCard(String eM, String cN, float b, float wL) {
        email = eM;
        card_number = cN;
        balance = b;
        withdraw_limit = wL;
    }

    public String getEmail() {
        return email;
    }

    public String getCardNumber() {
        return card_number;
    }

    public void setBalance(float amount) {
        this.balance += amount;
    }

    public float getBalance() {
        return balance;
    }

    public float getWithdrawLimit() {
        return withdraw_limit;
    }

    public void setWithdrawLimit(float new_limit) {
        withdraw_limit = new_limit;
    }

}
