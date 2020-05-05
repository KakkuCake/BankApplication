package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SavingsAccountActivity extends AppCompatActivity {

    private Button button_add_money_s, button_check_interest_rate, button_check_transactions_s;
    private TextView textview_transactions_s;
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

        final Account account2 = bank.returnAccount(account_number);
        textview_transactions_s = findViewById(R.id.textview_transactions_s);
        button_check_transactions_s = findViewById(R.id.button_check_transactions_s);
        button_check_transactions_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview_transactions_s.setText(getString(R.string.balance) + ": " + account2.balance + "€\n\n"+ bank.readTransaction(SavingsAccountActivity.this, account_number));
                textview_transactions_s.setMovementMethod(new ScrollingMovementMethod());

            }
        });

    }

    private void startActivity1() {
        Intent intent = new Intent(SavingsAccountActivity.this, AddMoneyActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

    private void showInterestRate() {
        String sentence = getString(R.string.interestRateIs) + " " + bank.getInterestRate(account_number) + "%";
        Toast.makeText(this, sentence, Toast.LENGTH_LONG).show();
    }

}
