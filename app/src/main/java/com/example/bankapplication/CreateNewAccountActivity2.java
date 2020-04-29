package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class CreateNewAccountActivity2 extends AppCompatActivity {

    private Button button_create_new_account;
    String getEmail, account_type;
    private TextInputLayout account_number, balance, credit;

    SessionManager sessionManager;
    Validation validator = new Validation(this);
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account2);

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
                    final String account_number = account_numberInput;
                    final String balance = balanceInput;
                    final String credit = creditInput;

                if (account_type.equals("regularAccount")) {
                    database.addRegularAccount(v, email, account_number, balance);
                } else if (account_type.equals("creditAccount")) {
                    database.addCreditAccount(v, email, account_number, balance, credit);
                } else if (account_type.equals("savingsAccount")) {
                    database.addSavingsAccount(v, email, account_number, balance);
                }
            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();

    }

}