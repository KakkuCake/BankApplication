package com.example.bankapplication;

public abstract class Account {
    private String acNumber;
    int balance;

    public Account(String aN, int b) {
        acNumber = aN;
        balance = b;
        System.out.println("Account created.");
    }

    public String getAcNumber() {
        return acNumber;
    }

    public void setBalance(int amount) {
        if (0 <= balance - amount)
            this.balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}
