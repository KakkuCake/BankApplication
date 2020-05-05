package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class CreateNewCreditAccountActivity extends AppCompatActivity {

    private Button button_create_new_account;
    String getEmail, account_type;
    private TextInputLayout account_number, balance, credit;
    Bank bank = new Bank();

    SessionManager sessionManager;
    Validation validator = new Validation(this);
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_credit_account);

        Intent intent = getIntent();
        account_type = intent.getStringExtra(CreateNewAccountActivity.ACCOUNT_TYPE);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);

        account_number = findViewById(R.id.account_number);
        balance = findViewById(R.id.balance);
        credit = findViewById(R.id.credit);

        button_create_new_account = findViewById(R.id.button_create_new_account);
        button_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account_numberInput = account_number.getEditText().getText().toString().trim();
                String balanceInput = balance.getEditText().getText().toString().trim();
                String creditInput = credit.getEditText().getText().toString().trim();

                if (!validator.validateAccountNumber(account_numberInput) | !validator.validateBalance(balanceInput) | !validator.validateCredit(creditInput)) {
                    return;
                }

                final String email = getEmail;
                final String balance = balanceInput;
                final String credit = creditInput;

                final String account_number = "C" + account_numberInput;
                database.addCreditAccount(v, email, account_number, balance, credit);
                bank.writeTransaction(account_number, "Account created, first deposit: ", balance, CreateNewCreditAccountActivity.this);

            }
        });

    }

}