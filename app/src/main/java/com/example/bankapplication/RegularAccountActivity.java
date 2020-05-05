package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegularAccountActivity extends AppCompatActivity {

    private Button button_transfer_r, button_add_money_r, button_check_transactions_r;
    private TextView textview_transactions_r;
    public static final String ACCOUNT_NUMBER = "com.example.bankapplication.ACCOUNT_NUMBER";
    String account_number;
    Bank bank = new Bank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_account);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        account_number = intent.getStringExtra(BankActionsFragment.ACCOUNT_NUMBER);

        button_transfer_r  = findViewById(R.id.button_transfer_r);
        button_transfer_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity1();
            }
        });

        button_add_money_r  = findViewById(R.id.button_add_money_r);
        button_add_money_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity2();
            }
        });

        final Account account2 = bank.returnAccount(account_number);
        textview_transactions_r = findViewById(R.id.textview_transactions_r);
        button_check_transactions_r = findViewById(R.id.button_check_transactions_r);
        button_check_transactions_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview_transactions_r.setText(getString(R.string.balance) + ": " + account2.balance + "€\n\n"+ bank.readTransaction(RegularAccountActivity.this, account_number));
                textview_transactions_r.setMovementMethod(new ScrollingMovementMethod());

            }
        });
    }

    private void startActivity1() {
        Intent intent = new Intent(RegularAccountActivity.this, AccountNumberActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }



    private void startActivity2() {
        Intent intent = new Intent(RegularAccountActivity.this, AddMoneyActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

}
