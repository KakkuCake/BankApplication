package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SavingsAccountActivity extends AppCompatActivity {

    private Button button_add_money_s, button_check_interest_rate;
    public static final String ACCOUNT_NUMBER = "com.example.bankapplication.ACCOUNT_NUMBER";
    String account_number;

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_account);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        account_number = intent.getStringExtra(BankActionsFragment.ACCOUNT_NUMBER);

        button_add_money_s  = findViewById(R.id.button_add_money_s);
        button_add_money_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity1();
            }
        });

        button_check_interest_rate = findViewById(R.id.button_check_interest_rate);
        button_check_interest_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterestRate();
            }
        });
    }

    private void startActivity1() {
        Intent intent = new Intent(SavingsAccountActivity.this, AddMoneyActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

    private void showInterestRate() {
        String sentence = "Your interest rate is " + bank.getInterestRate(account_number) + "%";
        Toast.makeText(this, sentence, Toast.LENGTH_LONG).show();
    }

}
