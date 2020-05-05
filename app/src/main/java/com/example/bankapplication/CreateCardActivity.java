package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class CreateCardActivity extends AppCompatActivity {

    private TextInputLayout balance, withdraw_limit;
    private Button button_create_card;
    String mEmail;
    Validation validator = new Validation(this);
    Database database = new Database(this);
    SessionManager sessionManager;
    HelperClass helper = new HelperClass();
    Bank bank = new Bank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail(); // Selvitetään kirjautuneen käyttäjän etunimi ja sähköposti.
        mEmail = user.get(sessionManager.EMAIL);

        balance = findViewById(R.id.balance);
        withdraw_limit = findViewById(R.id.withdraw_limit);

        button_create_card = findViewById(R.id.button_create_card);
        button_create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String balanceInput = balance.getEditText().getText().toString().trim();
                String wLimitInput = withdraw_limit.getEditText().getText().toString().trim();

                if (!validator.validateBalance(balanceInput) | !validator.validateWithDrawLimit(wLimitInput)) {
                    return;
                }

                final String balance = balanceInput;
                final String limit = wLimitInput;

                final String card_number = helper.getCardNumber();

                database.addBankCard(v, mEmail, card_number, balance, limit);
                bank.writeTransaction(card_number, "Card created, first deposit: ", balance, CreateCardActivity.this);

            }
        });
    }


}
