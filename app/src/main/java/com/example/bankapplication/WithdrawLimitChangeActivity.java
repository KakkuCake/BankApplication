package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class WithdrawLimitChangeActivity extends AppCompatActivity {

    private Button button_limit;
    private TextInputLayout withdraw_limit;
    String mEmail;
    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_limit_change);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        mEmail = user.get(sessionManager.EMAIL);

        withdraw_limit = findViewById(R.id.withdraw_limit);

        button_limit = findViewById(R.id.button_limit);
        button_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String limitInput= withdraw_limit.getEditText().getText().toString().trim();

                if (!validator.validateWithDrawLimit(limitInput)) {
                    return;
                }

                float amount = Float.parseFloat(limitInput);
                bank.setNewWithdrawLimit(mEmail, amount);

                BankCard myCard = bank.returnCard(mEmail);
                float new_withdraw_limit = myCard.getWithdrawLimit();

                String my_limit = String.valueOf(new_withdraw_limit);
                database.saveLimitBankCard(v, mEmail, my_limit);

            }
        });

    }

}