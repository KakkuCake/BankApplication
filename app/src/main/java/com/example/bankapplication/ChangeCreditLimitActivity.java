package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class ChangeCreditLimitActivity extends AppCompatActivity {

    private Button button_credit;
    private TextInputLayout credit;
    String account_number;

    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credit_limit);


        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        account_number = intent.getStringExtra(CreditAccountActivity.ACCOUNT_NUMBER);


        credit = findViewById(R.id.credit);

        button_credit = findViewById(R.id.button_credit);
        button_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String credit_string = credit.getEditText().getText().toString().trim();
                if (!validator.validateCredit(credit_string)) {
                    return;
                }

                float new_credit_limit = Float.parseFloat(credit_string);
                bank.setNewCreditLimit(account_number, new_credit_limit);
                database.changeCreditLimit(v, account_number, credit_string);

            }
        });

    }

    }

