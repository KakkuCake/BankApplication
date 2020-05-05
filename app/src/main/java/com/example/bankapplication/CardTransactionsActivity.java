package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardTransactionsActivity extends AppCompatActivity {

    private Button button_add_money_b, button_change_withdraw_limit, button_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_transactions);

        button_add_money_b = findViewById(R.id.button_add_money_b);
        button_add_money_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity1();
            }
        });

        button_change_withdraw_limit = findViewById(R.id.button_change_withdraw_limit);
        button_change_withdraw_limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity2();
            }
        });

        button_withdraw = findViewById(R.id.button_withdraw);
        button_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity3();
            }
        });


    }

    protected void startActivity1() {
        Intent intent = new Intent(this, DepositCardActivity.class);
        startActivity(intent);
    }

    protected void startActivity2() {
        Intent intent = new Intent(this,  WithdrawLimitChangeActivity.class);
        startActivity(intent);
    }

    protected void startActivity3() {
        Intent intent = new Intent(this,  CardWithdrawActivity.class);
        startActivity(intent);
    }
}
