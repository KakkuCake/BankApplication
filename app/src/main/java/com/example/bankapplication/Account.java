package com.example.bankapplication;

public abstract class Account {

    protected String email, acNumber;
    protected float balance;

    public Account(String eM, String aN, float b) {
        email = eM;
        acNumber = aN;
        balance = b;
        System.out.println("Account created.");
    }

    public String getEmail() {
        return email;
    }

    public String getAcNumber() {
        return acNumber;
    }

    public void setBalance(float amount) {
        if (0 <= balance - amount)
            this.balance += amount;
    }

    public float getBalance() {
        return balance;
    }
}
