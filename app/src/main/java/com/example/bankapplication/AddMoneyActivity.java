package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

public class AddMoneyActivity extends AppCompatActivity {

    private Button button_add_money2;
    private TextInputLayout balance;
    String account_number;

    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        account_number = intent.getStringExtra(BankActionsFragment.ACCOUNT_NUMBER);

        balance = findViewById(R.id.balance);

        button_add_money2 = findViewById(R.id.button_add_money2);
        button_add_money2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String deposit = balance.getEditText().getText().toString().trim();

                System.out.println("deposit: " + deposit);

                if (!validator.validateBalance(deposit)) {
                    return;
                }

                float amount = Float.parseFloat(deposit);
                bank.depositMoney(account_number, amount);
                bank.writeTransaction(account_number, "Deposit: ", "+"+ amount, AddMoneyActivity.this);

                Account myAccount = bank.returnAccount(account_number);
                float accounts_balance = myAccount.getBalance();//Let's get user's new balance from bank so we can count the new balance.


                String new_balance_string = String.valueOf(accounts_balance);

                char first_letter = account_number.charAt(0);
                String account_mark = "" + first_letter;  // Selvitetään käyttäjätunnuksen ensimmäinen kirjain (R, C tai S) riippuen tilityypistä.
                if (account_mark.equals("R")) {
                    database.addMoneyRegularAccount(v, account_number, new_balance_string); //Finally let's save the new balance to database.
                } else if (account_mark.equals("C")) {
                    database.addMoneyCreditAccount(v, account_number, new_balance_string); //Finally let's save the new balance to database.
                } else if (account_mark.equals("S")) {
                    database.addMoneySavingsAccount(v, account_number, new_balance_string);
                }
            }
        });

    }

}
