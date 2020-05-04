package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreditAccountActivity extends AppCompatActivity {

    private Button button_transfer_c, button_account_transfer_c, button_add_money_c, button_change_credit_limit;
    public static final String ACCOUNT_NUMBER = "com.example.bankapplication.ACCOUNT_NUMBER";
    String account_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_account);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        account_number = intent.getStringExtra(BankActionsFragment.ACCOUNT_NUMBER);

        button_transfer_c  = findViewById(R.id.button_transfer_c);
        button_transfer_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity1();
            }
        });

        button_add_money_c  = findViewById(R.id.button_add_money_c);
        button_add_money_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity3();
            }
        });

        button_change_credit_limit  = findViewById(R.id.button_change_credit_limit);
        button_change_credit_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity4();
            }
        });
    }

    private void startActivity1() {
        Intent intent = new Intent(CreditAccountActivity.this, AccountNumberActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

    private void startActivity3() {
        Intent intent = new Intent(CreditAccountActivity.this, AddMoneyActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

    private void startActivity4() {
        Intent intent = new Intent(CreditAccountActivity.this, ChangeCreditLimitActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

}
