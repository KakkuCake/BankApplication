package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class TransferActivity extends AppCompatActivity {

    private Button button_transfer;
    private TextInputLayout balance;
    String my_account_number;

    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        button_transfer = findViewById(R.id.button_transfer);
        balance = findViewById(R.id.balance);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        my_account_number = intent.getStringExtra(CreditAccountActivity.ACCOUNT_NUMBER);
        System.out.println(my_account_number);

        button_transfer = findViewById(R.id.button_transfer);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String balanceInput = balance.getEditText().getText().toString().trim(); ;

                if (!validator.validateBalance(balanceInput)) {
                    return;
                }

                float amount = Float.parseFloat(balanceInput);
                bank.withdrawMoney(my_account_number, amount);

                Account myAccount = bank.returnAccount(my_account_number);
                float my_balance = myAccount.getBalance();//Let's get user's new balance from bank so we can count the new balance.
                String my_new_balance = String.valueOf(my_balance);
                database.withdrawMoney(v, my_account_number, my_new_balance);

                float ac_balance = Float.parseFloat(getBalance()); //This is the balance of the account the user put in the previous activity.
                float ac_new_balance = ac_balance + amount;
                String new_balance_string = String.valueOf(ac_new_balance);

                database.transferMoney(v, "R1111111", new_balance_string);

            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();
    }

    protected String getBalance() {
        HelperClass helper = HelperClass.getInstance();
        String ac_balance = helper.getBalance();
        return  ac_balance;
    }
}
