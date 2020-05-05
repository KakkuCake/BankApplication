package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class CardWithdrawActivity extends AppCompatActivity {

    private Button button_card_withdraw;
    private TextInputLayout balance;
    String mEmail;
    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_withdraw);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        mEmail = user.get(sessionManager.EMAIL);

        balance = findViewById(R.id.balance);

        button_card_withdraw = findViewById(R.id.button_card_withdraw);
        button_card_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String withdrawInput = balance.getEditText().getText().toString().trim();

                if (!validator.validateWithdraw(withdrawInput)) {
                    return;
                }

                float amount = Float.parseFloat(withdrawInput);
                BankCard myCard = bank.returnCard(mEmail);

                if (amount > myCard.getBalance()) {
                    balance.setError(getString(R.string.notEnoughBalance));
                } else if (amount > myCard.getWithdrawLimit()) {
                    balance.setError(getString(R.string.limitError));
                } else {

                    bank.withdrawCard(mEmail, amount);
                    float new_balance = myCard.getBalance();

                    String my_balance = String.valueOf(new_balance);
                    database.saveBalanceBankCard(v, mEmail, my_balance);
                    bank.writeTransaction(myCard.card_number, "Card Withdraw: ", "-"+ amount, CardWithdrawActivity.this);
                }

            }
        });

    }

}
