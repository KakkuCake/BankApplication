package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class CreateNewRegularAccountActivity extends AppCompatActivity {

    private Button button_create_new_account;
    String getEmail;
    private TextInputLayout account_number, balance;

    SessionManager sessionManager;
    Validation validator = new Validation(this);
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_regular_account);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);

        account_number = findViewById(R.id.account_number);
        balance = findViewById(R.id.balance);

        button_create_new_account = findViewById(R.id.button_create_new_account);
        button_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String account_numberInput = account_number.getEditText().getText().toString().trim();
                    String balanceInput = balance.getEditText().getText().toString().trim();

                    if (!validator.validateAccountNumber(account_numberInput) | !validator.validateBalance(balanceInput)) {
                        return;
                    }

                    final String email = getEmail;
                    final String balance = balanceInput;
                    final String account_number = "R" + account_numberInput;  // Käytetään tunnuksia tilinumerossa, jotta tiedetään minkä tyyppinen tili on käytössä.
                    database.addRegularAccount(v, email, account_number, balance);
            }
        });


    }

}
