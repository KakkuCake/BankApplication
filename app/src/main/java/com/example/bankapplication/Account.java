package com.example.bankapplication;

public abstract class Account {
    private String email, acNumber;
    int balance;

    public Account(String eM, String aN, int b) {
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

    public void setBalance(int amount) {
        if (0 <= balance - amount)
            this.balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}
