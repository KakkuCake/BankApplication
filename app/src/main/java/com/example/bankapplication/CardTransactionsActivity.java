package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class CardTransactionsActivity extends AppCompatActivity {

    private Button button_add_money_b, button_change_withdraw_limit, button_withdraw, button_check_transactions_card;
    private TextView cardNumText, cardHolderName, transactions_textview;
    Bank bank = new Bank();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_transactions);
        cardNumText = findViewById(R.id.cardNumText);
        cardHolderName = findViewById(R.id.cardHolderName);
        transactions_textview = findViewById(R.id.transactions_textview);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String mEmail = user.get(sessionManager.EMAIL);
        String fName = user.get(sessionManager.FIRST_NAME);

        final BankCard card = bank.returnCard(mEmail);
        final String carNumberText = card.getCardNumber();
        final float balance = card.balance;
        cardNumText.setText(carNumberText);
        cardHolderName.setText(fName);

        button_add_money_b = findViewById(R.id.button_add_money_b);
        button_add_money_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity1();
            }
        });

        button_change_withdraw_limit = findViewById(R.id.button_change_withdraw_limit);
        button_change_withdraw_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity2();
            }
        });

        button_withdraw = findViewById(R.id.button_withdraw);
        button_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity3();
            }
        });

        button_check_transactions_card = findViewById(R.id.button_check_transactions_card);
        button_check_transactions_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactions_textview.setText(getString(R.string.balance) + ": " + balance + "€\n\n"+ bank.readTransaction(CardTransactionsActivity.this, carNumberText));
                transactions_textview.setMovementMethod(new ScrollingMovementMethod());

            }
        });


    }

    protected void startActivity1() {
        Intent intent = new Intent(this, DepositCardActivity.class);
        startActivity(intent);
    }

    protected void startActivity2() {
        Intent intent = new Intent(this,  WithdrawLimitChangeActivity.class);
        startActivity(intent);
    }

    protected void startActivity3() {
        Intent intent = new Intent(this,  CardWithdrawActivity.class);
        startActivity(intent);
    }
}
