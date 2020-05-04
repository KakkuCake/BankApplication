package com.example.bankapplication;

public class SavingsAccount extends Account {

    private float interest_rate;

    public SavingsAccount(String eM, String aN, float b) {
        super(eM, aN, b);
        interest_rate = 1;  // Kaikilla luoduilla säästötileillä on kiinteä korko 1%.
    }

    public float getInterestRate() {
        return interest_rate;
    }

    public void setBalance(float amount) {
            this.balance += amount;
    }

}
