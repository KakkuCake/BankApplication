package com.example.bankapplication;

public abstract class Account {

    protected String email, acNumber;
    protected float balance;

    public Account(String eM, String aN, float b) {
        email = eM;
        acNumber = aN;
        balance = b;
    }

    public String getEmail() {
        return email;
    }

    public String getAcNumber() {
        return acNumber;
    }

    public void setBalance(float amount) {
            this.balance += amount;
    }

    public float getBalance() {
        return balance;
    }
}
