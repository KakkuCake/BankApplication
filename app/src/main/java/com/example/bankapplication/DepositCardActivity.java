package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class DepositCardActivity extends AppCompatActivity {

    private Button button_deposit;
    private TextInputLayout balance;
    String mEmail;
    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_card);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        mEmail = user.get(sessionManager.EMAIL);

        balance = findViewById(R.id.balance);

        button_deposit = findViewById(R.id.button_deposit);
        button_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String deposit = balance.getEditText().getText().toString().trim();

                if (!validator.validateBalance(deposit)) {
                    return;
                }

                float amount = Float.parseFloat(deposit);
                bank.depositCard(mEmail, amount);

                BankCard myCard = bank.returnCard(mEmail);
                float new_balance = myCard.getBalance();

                String my_balance = String.valueOf(new_balance);
                database.saveBalanceBankCard(v, mEmail, my_balance);

            }
        });

    }

}
