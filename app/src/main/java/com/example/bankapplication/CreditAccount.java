package com.example.bankapplication;

public class CreditAccount extends Account {

    private float credit;

    public CreditAccount(String eM, String aN, float b, float c) {
        super(eM, aN, b);
        credit = c;
    }

    public float getCreditLimit() {
        return credit;
    }

    public void setCreditLimit(float new_credit) {
        credit = new_credit;
    }

    public void setBalance(float amount) {
        if (-credit <= balance + amount)
            this.balance += amount;

    }

}
