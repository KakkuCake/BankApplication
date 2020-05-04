package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegularAccountActivity extends AppCompatActivity {

    private Button button_transfer_r, button_account_transfer_r, button_add_money_r;
    public static final String ACCOUNT_NUMBER = "com.example.bankapplication.ACCOUNT_NUMBER";
    String account_number;

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
                System.out.println("dsafadf");
            }
        });
        button_add_money_r  = findViewById(R.id.button_add_money_r);
        button_add_money_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity3();
            }
        });
    }
 /*
    private void startActivity1() {
        Intent intent = new Intent(RegularAccountActivity.this, TransferActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }
    */


    private void startActivity3() {
        Intent intent = new Intent(RegularAccountActivity.this, AddMoneyActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account_number);
        startActivity(intent);
    }

}
